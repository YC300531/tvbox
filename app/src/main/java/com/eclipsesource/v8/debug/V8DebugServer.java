package com.eclipsesource.v8.debug;

import androidx.base.a.b;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.V8Value;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
public class V8DebugServer {
    private static final String DEBUG_BREAK_HANDLER = "__j2v8_debug_handler";
    public static String DEBUG_OBJECT_NAME = "__j2v8_Debug";
    private static final String HEADER_EMBEDDING_HOST = "Embedding-Host: ";
    private static final String HEADER_PROTOCOL_VERSION = "Protocol-Version: ";
    private static final String HEADER_TYPE = "Type: ";
    private static final String HEADER_V8_VERSION = "V8-Version: ";
    private static final String J2V8_VERSION = "4.0.0";
    private static final String MAKE_BREAK_EVENT = "__j2v8_MakeBreakEvent";
    private static final String MAKE_COMPILE_EVENT = "__j2v8_MakeCompileEvent";
    private static final int PROTOCOL_BUFFER_SIZE = 4096;
    private static final Charset PROTOCOL_CHARSET;
    private static final byte[] PROTOCOL_CONTENT_LENGTH_BYTES;
    private static final String PROTOCOL_CONTENT_LENGTH_HEADER = "Content-Length:";
    private static final String PROTOCOL_EOL = "\r\n";
    private static final byte[] PROTOCOL_EOL_BYTES;
    private static final String PROTOCOL_VERSION = "1";
    private static final String SET_LISTENER = "setListener";
    private static final String V8_DEBUG_OBJECT = "Debug";
    private static final String V8_VERSION = "4.10.253";
    private Socket client;
    private V8Object debugObject;
    private V8Object runningStateDcp;
    private V8 runtime;
    private ServerSocket server;
    private V8Object stoppedStateDcp;
    private boolean waitForConnection;
    private Object clientLock = new Object();
    private boolean traceCommunication = false;
    private List<String> requests = new LinkedList();

    public static class fun1 {
    }

    public class ClientLoop implements Runnable {
        private int from;

        private ClientLoop() {
        }

        public ClientLoop(V8DebugServer v8DebugServer, 1 r2) {
            this();
        }

        private int indexOf(byte[] bArr, byte[] bArr2, int i, int i2) {
            int i3;
            int length = bArr.length;
            while (i < i2) {
                while (i3 <= length) {
                    if (i3 == length) {
                        return i;
                    }
                    int i4 = i + i3;
                    i3 = (i4 < i2 && bArr2[i4] == bArr[i3]) ? i3 + 1 : 0;
                    i++;
                }
                i++;
            }
            return -1;
        }

        private byte[] join(byte[] bArr, byte[] bArr2, int i, int i2) {
            byte[] bArr3 = new byte[bArr.length + i2];
            System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
            System.arraycopy(bArr2, i, bArr3, bArr.length, i2);
            return bArr3;
        }

        private void processClientRequests() {
            InputStream inputStream;
            byte[] bArr = new byte[0];
            byte[] bArr2 = new byte[4096];
            synchronized (clientLock) {
                inputStream = client.getInputStream();
            }
            byte[] bArr3 = bArr;
            int i = 0;
            boolean z = false;
            int i2 = -1;
            while (true) {
                int read = inputStream.read(bArr2, i, 4096 - i);
                if (read <= 0) {
                    return;
                }
                int i3 = read + i;
                this.from = 0;
                do {
                    if (i2 < 0) {
                        i2 = readContentLength(bArr2, i3);
                        if (i2 < 0) {
                            break;
                        }
                    }
                    if (!z && !(z = skipToolInfo(bArr2, i3))) {
                        break;
                    }
                    int min = Math.min(i2 - bArr3.length, i3 - this.from);
                    bArr3 = join(bArr3, bArr2, this.from, min);
                    this.from += min;
                    if (bArr3.length == i2) {
                        String str = new String(bArr3, V8DebugServer.PROTOCOL_CHARSET);
                        synchronized (requests) {
                            requests.add(str);
                        }
                        bArr3 = bArr;
                        z = false;
                        i2 = -1;
                    }
                } while (this.from < i3);
                int i4 = this.from;
                if (i4 < i3) {
                    System.arraycopy(bArr2, i4, bArr2, 0, i3 - i4);
                    i = i3 - this.from;
                } else {
                    i = 0;
                }
            }
        }

        private int readContentLength(byte[] bArr, int i) {
            int length;
            int indexOf;
            int indexOf2 = indexOf(V8DebugServer.PROTOCOL_CONTENT_LENGTH_BYTES, bArr, this.from, i);
            if (indexOf2 >= 0 && (indexOf = indexOf(V8DebugServer.PROTOCOL_EOL_BYTES, bArr, (length = indexOf2 + V8DebugServer.PROTOCOL_CONTENT_LENGTH_BYTES.length), i)) >= 0) {
                String str = new String(bArr, length, indexOf - length, V8DebugServer.PROTOCOL_CHARSET);
                try {
                    int parseInt = Integer.parseInt(str.trim());
                    this.from = indexOf + V8DebugServer.PROTOCOL_EOL_BYTES.length;
                    return parseInt;
                } catch (Exception unused) {
                    StringBuilder f = b.f("Invalid content length header: '", str, "' in message");
                    f.append(new String(bArr, V8DebugServer.PROTOCOL_CHARSET));
                    throw new IOException(f.toString());
                }
            }
            return -1;
        }

        private boolean skipToolInfo(byte[] bArr, int i) {
            int indexOf = indexOf(V8DebugServer.PROTOCOL_EOL_BYTES, bArr, this.from, i);
            if (indexOf < 0) {
                return false;
            }
            this.from = indexOf + V8DebugServer.PROTOCOL_EOL_BYTES.length;
            return true;
        }

        private void startHandshake() {
            sendMessage("V8-Version: 4.10.253\r\nProtocol-Version: 1\r\nEmbedding-Host: j2v8 4.0.0\r\nType: connect\r\n", "");
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Socket accept = server.accept();
                    accept.setTcpNoDelay(true);
                    synchronized (clientLock) {
                        client = accept;
                        waitForConnection = false;
                        clientLock.notifyAll();
                    }
                    startHandshake();
                    processClientRequests();
                } catch (Exception e) {
                    synchronized (clientLock) {
                        if (client != null) {
                            try {
                                client.close();
                            } catch (IOException unused) {
                            }
                            client = null;
                        }
                        logError(e);
                    }
                }
            }
        }
    }

    public class EventHandler implements JavaVoidCallback {
        private EventHandler() {
        }

        public EventHandler(V8DebugServer v8DebugServer, 1 r2) {
            this();
        }

        private void safeRelease(Releasable releasable) {
            if (releasable != null) {
                releasable.release();
            }
        }

        @Override
        public void invoke(V8Object v8Object, V8Array v8Array) {
            Releasable releasable;
            int integer;
            V8Object object;
            if (v8Array == null || v8Array.isUndefined()) {
                return;
            }
            V8Object v8Object2 = null;
            try {
                integer = v8Array.getInteger(0);
                object = v8Array.getObject(1);
            } catch (Exception e) {
                e = e;
                releasable = null;
            } catch (Throwable th) {
                th = th;
                releasable = null;
            }
            try {
                V8Object object2 = v8Array.getObject(2);
                if (traceCommunication) {
                    String str = "unknown";
                    switch (integer) {
                        case 1:
                            str = "Break";
                            break;
                        case 2:
                            str = "Exception";
                            break;
                        case 3:
                            str = "NewFunction";
                            break;
                        case 4:
                            str = "BeforeCompile";
                            break;
                        case 5:
                            str = "AfterCompile";
                            break;
                        case 6:
                            str = "CompileError";
                            break;
                        case 7:
                            str = "PromiseEvent";
                            break;
                        case 8:
                            str = "AsyncTaskEvent";
                            break;
                    }
                    System.out.println("V8 has emmitted an event of type " + str);
                }
                if (!isConnected()) {
                    safeRelease(object);
                    safeRelease(object2);
                    return;
                }
                if (integer == 1) {
                    enterBreakLoop(object, object2);
                } else if (integer == 5 || integer == 6) {
                    sendCompileEvent(object2);
                }
                safeRelease(object);
                safeRelease(object2);
            } catch (Exception e2) {
                e = e2;
                releasable = null;
                v8Object2 = object;
                try {
                    logError(e);
                    safeRelease(v8Object2);
                    safeRelease(releasable);
                } catch (Throwable th2) {
                    th = th2;
                    safeRelease(v8Object2);
                    safeRelease(releasable);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                releasable = null;
                v8Object2 = object;
                safeRelease(v8Object2);
                safeRelease(releasable);
                throw th;
            }
        }
    }

    static {
        Charset forName = Charset.forName("UTF-8");
        PROTOCOL_CHARSET = forName;
        PROTOCOL_EOL_BYTES = PROTOCOL_EOL.getBytes(forName);
        PROTOCOL_CONTENT_LENGTH_BYTES = PROTOCOL_CONTENT_LENGTH_HEADER.getBytes(forName);
    }

    public V8DebugServer(V8 v8, int i, boolean z) {
        this.runtime = v8;
        this.waitForConnection = z;
        V8Object object = v8.getObject(DEBUG_OBJECT_NAME);
        if (object == null) {
            System.err.println("Cannot initialize debugger server - global debug object not found.");
            return;
        }
        try {
            this.debugObject = object.getObject(V8_DEBUG_OBJECT);
            object.close();
            v8.executeVoidScript("(function() {\n " + DEBUG_OBJECT_NAME + ".Debug. " + MAKE_BREAK_EVENT + " = function (break_id,breakpoints_hit) {\n  return new " + DEBUG_OBJECT_NAME + ".BreakEvent(break_id,breakpoints_hit);\n }\n " + DEBUG_OBJECT_NAME + ".Debug. " + MAKE_COMPILE_EVENT + " = function(script,type) {\n  var scripts = " + DEBUG_OBJECT_NAME + ".Debug.scripts()\n  for (var i in scripts) {\n   if (scripts[i].id == script.id()) {\n     return new " + DEBUG_OBJECT_NAME + ".CompileEvent(scripts[i], type);\n   }\n  }\n  return {toJSONProtocol: function() {return ''}}\n }\n})()");
            try {
                this.server = new ServerSocket(i);
            } catch (Exception e) {
                logError(e);
            }
        } catch (Throwable th) {
            object.close();
            throw th;
        }
    }

    public static void configureV8ForDebugging() {
        try {
            V8.setFlags("-expose-debug-as=" + DEBUG_OBJECT_NAME);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void enterBreakLoop(V8Object v8Object, V8Object v8Object2) {
        V8Object v8Object3;
        try {
            V8Array v8Array = new V8Array(this.runtime);
            v8Array.push(false);
            this.stoppedStateDcp = v8Object.executeObjectFunction("debugCommandProcessor", v8Array);
            v8Array.close();
            int integer = v8Object.getInteger("break_id");
            V8Array array = v8Object2.getArray("break_points_hit_");
            V8Array v8Array2 = new V8Array(this.runtime);
            try {
                v8Array2.push(integer);
                v8Array2.push((V8Value) array);
                v8Object3 = this.debugObject.executeObjectFunction(MAKE_BREAK_EVENT, v8Array2);
                try {
                    String executeStringFunction = v8Object3.executeStringFunction("toJSONProtocol", null);
                    if (this.traceCommunication) {
                        System.out.println("Sending event (Break):\n" + executeStringFunction);
                    }
                    sendJson(executeStringFunction);
                    v8Array2.close();
                    array.close();
                    v8Object3.close();
                    while (isConnected() && !this.stoppedStateDcp.executeBooleanFunction("isRunning", null)) {
                        try {
                            processRequests(10L);
                        } catch (InterruptedException unused) {
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    v8Array2.close();
                    array.close();
                    if (v8Object3 != null) {
                        v8Object3.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                v8Object3 = null;
            }
        } finally {
            this.stoppedStateDcp.close();
            this.stoppedStateDcp = null;
        }
    }

    public boolean isConnected() {
        boolean z;
        Socket socket;
        synchronized (this.clientLock) {
            z = (this.server == null || (socket = this.client) == null || !socket.isConnected()) ? false : true;
        }
        return z;
    }

    private void processRequest(String str) {
        if (this.traceCommunication) {
            PrintStream printStream = System.out;
            StringBuilder d = b.d("Got message: \n");
            d.append(str.substring(0, Math.min(str.length(), 1000)));
            printStream.println(d.toString());
        }
        V8Array v8Array = new V8Array(this.runtime);
        v8Array.push(str);
        V8Object v8Object = this.stoppedStateDcp;
        if (v8Object == null) {
            v8Object = this.runningStateDcp;
        }
        String obj = v8Object.executeFunction("processDebugJSONRequest", v8Array).toString();
        if (this.stoppedStateDcp == null && obj.contains("\"running\":false")) {
            obj = obj.replace("\"running\":false", "\"running\":true").replace("\"success\":true", "\"success\":false").replace("{\"", "{\"message\":\"Client requested suspension is not supported on J2V8.\",\"");
            v8Object.add("running_", true);
        }
        if (this.traceCommunication) {
            PrintStream printStream2 = System.out;
            StringBuilder d2 = b.d("Returning response: \n");
            d2.append(obj.substring(0, Math.min(obj.length(), 1000)));
            printStream2.println(d2.toString());
        }
        sendJson(obj);
    }

    public void sendCompileEvent(V8Object v8Object) {
        Throwable th;
        V8Object v8Object2;
        if (!isConnected()) {
            return;
        }
        int integer = v8Object.getInteger("type_");
        V8Object object = v8Object.getObject("script_");
        V8Array v8Array = new V8Array(this.runtime);
        try {
            v8Array.push((V8Value) object);
            v8Array.push(integer);
            v8Object2 = this.debugObject.executeObjectFunction(MAKE_COMPILE_EVENT, v8Array);
            try {
                String executeStringFunction = v8Object2.executeStringFunction("toJSONProtocol", null);
                if (this.traceCommunication) {
                    System.out.println("Sending event (CompileEvent):\n" + executeStringFunction.substring(0, Math.min(executeStringFunction.length(), 1000)));
                }
                if (executeStringFunction.length() > 0) {
                    sendJson(executeStringFunction);
                }
                v8Array.close();
                object.close();
                v8Object2.close();
            } catch (Throwable th2) {
                th = th2;
                v8Array.close();
                object.close();
                if (v8Object2 != null) {
                    v8Object2.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            v8Object2 = null;
        }
    }

    private void sendJson(String str) {
        sendMessage("", str.replace("\\/", "/"));
    }

    public void sendMessage(String str, String str2) {
        synchronized (this.clientLock) {
            if (!isConnected()) {
                throw new IOException("There is no connected client.");
            }
            Charset charset = PROTOCOL_CHARSET;
            byte[] bytes = str2.getBytes(charset);
            this.client.getOutputStream().write((str + PROTOCOL_CONTENT_LENGTH_HEADER + Integer.toString(bytes.length) + PROTOCOL_EOL + PROTOCOL_EOL).getBytes(charset));
            if (bytes.length > 0) {
                this.client.getOutputStream().write(bytes);
            }
        }
    }

    private void setupEventHandler() {
        V8Array v8Array;
        Throwable th;
        V8Function v8Function;
        this.debugObject.registerJavaMethod(new EventHandler(this, null), DEBUG_BREAK_HANDLER);
        try {
            v8Function = (V8Function) this.debugObject.getObject(DEBUG_BREAK_HANDLER);
            try {
                v8Array = new V8Array(this.runtime);
                try {
                    v8Array.push((V8Value) v8Function);
                    this.debugObject.executeFunction(SET_LISTENER, v8Array);
                    if (v8Function != null && !v8Function.isReleased()) {
                        v8Function.close();
                    }
                    if (v8Array.isReleased()) {
                        return;
                    }
                    v8Array.close();
                } catch (Throwable th2) {
                    th = th2;
                    if (v8Function != null && !v8Function.isReleased()) {
                        v8Function.close();
                    }
                    if (v8Array != null && !v8Array.isReleased()) {
                        v8Array.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                v8Array = null;
                th = th3;
            }
        } catch (Throwable th4) {
            v8Array = null;
            th = th4;
            v8Function = null;
        }
    }

    public int getPort() {
        ServerSocket serverSocket = this.server;
        if (serverSocket == null || !serverSocket.isBound()) {
            return -1;
        }
        return this.server.getLocalPort();
    }

    public void logError(Throwable th) {
        th.printStackTrace();
    }

    public void processRequests(long j) {
        String[] strArr;
        if (this.server == null) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        while (true) {
            synchronized (this.requests) {
                List<String> list = this.requests;
                strArr = (String[]) list.toArray(new String[list.size()]);
                this.requests.clear();
            }
            for (String str : strArr) {
                try {
                    processRequest(str);
                } catch (Exception e) {
                    logError(e);
                }
            }
            if (strArr.length <= 0) {
                if (j > 0) {
                    Thread.sleep(10L);
                }
                if (j <= 0 || currentTimeMillis + j <= System.currentTimeMillis()) {
                    return;
                }
            }
        }
    }

    public void setTraceCommunication(boolean z) {
        this.traceCommunication = z;
    }

    public void start() {
        if (this.server == null) {
            return;
        }
        boolean z = this.waitForConnection;
        Thread thread = new Thread(new ClientLoop(this, null), "J2V8 Debugger Server");
        thread.setDaemon(true);
        thread.start();
        setupEventHandler();
        V8 v8 = this.runtime;
        StringBuilder d = b.d("(function() {return new ");
        d.append(DEBUG_OBJECT_NAME);
        d.append(".DebugCommandProcessor(null, true)})()");
        this.runningStateDcp = v8.executeObjectScript(d.toString());
        if (z) {
            synchronized (this.clientLock) {
                while (this.waitForConnection) {
                    try {
                        this.clientLock.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
            try {
                processRequests(100L);
            } catch (InterruptedException unused2) {
            }
        }
    }

    public void stop() {
        try {
            this.server.close();
            synchronized (this.clientLock) {
                Socket socket = this.client;
                if (socket != null) {
                    socket.close();
                    this.client = null;
                }
            }
        } catch (IOException e) {
            logError(e);
        }
        V8Object v8Object = this.runningStateDcp;
        if (v8Object != null) {
            v8Object.close();
            this.runningStateDcp = null;
        }
        V8Object v8Object2 = this.debugObject;
        if (v8Object2 != null) {
            v8Object2.close();
            this.debugObject = null;
        }
        V8Object v8Object3 = this.stoppedStateDcp;
        if (v8Object3 != null) {
            v8Object3.close();
            this.stoppedStateDcp = null;
        }
    }
}
