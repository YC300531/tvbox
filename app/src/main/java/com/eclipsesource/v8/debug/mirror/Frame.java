package com.eclipsesource.v8.debug.mirror;

import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
public class Frame extends Mirror {
    private static final String ARGUMENT_COUNT = "argumentCount";
    private static final String ARGUMENT_NAME = "argumentName";
    private static final String ARGUMENT_VALUE = "argumentValue";
    private static final String COLUMN = "column";
    private static final String FUNC = "func";
    private static final String LINE = "line";
    private static final String LOCAL_COUNT = "localCount";
    private static final String LOCAL_NAME = "localName";
    private static final String LOCAL_VALUE = "localValue";
    private static final String NAME = "name";
    private static final String POSITION = "position";
    private static final String SCOPE = "scope";
    private static final String SCOPE_COUNT = "scopeCount";
    private static final String SCRIPT = "script";
    private static final String SOURCE_LOCATION = "sourceLocation";
    private static final String SOURCE_TEXT = "sourceText";

    public Frame(V8Object v8Object) {
        super(v8Object);
    }

    public int getArgumentCount() {
        return this.v8Object.executeIntegerFunction(ARGUMENT_COUNT, null);
    }

    public String getArgumentName(int i) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(i);
        try {
            return this.v8Object.executeStringFunction(ARGUMENT_NAME, v8Array);
        } finally {
            v8Array.close();
        }
    }

    public ValueMirror getArgumentValue(int i) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(i);
        AutoCloseable autoCloseable = null;
        try {
            V8Object executeObjectFunction = this.v8Object.executeObjectFunction(ARGUMENT_VALUE, v8Array);
            if (Mirror.isValue(executeObjectFunction)) {
                ValueMirror valueMirror = new ValueMirror(executeObjectFunction);
                v8Array.close();
                if (executeObjectFunction != null) {
                    executeObjectFunction.close();
                }
                return valueMirror;
            }
            throw new IllegalStateException("Argument value is not a ValueMirror");
        } catch (Throwable th) {
            v8Array.close();
            if (0 != 0) {
                autoCloseable.close();
            }
            throw th;
        }
    }

    public FunctionMirror getFunction() {
        V8Object v8Object = null;
        try {
            v8Object = this.v8Object.executeObjectFunction(FUNC, null);
            return new FunctionMirror(v8Object);
        } finally {
            if (v8Object != null) {
                v8Object.close();
            }
        }
    }

    public int getLocalCount() {
        return this.v8Object.executeIntegerFunction(LOCAL_COUNT, null);
    }

    public String getLocalName(int i) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(i);
        try {
            return this.v8Object.executeStringFunction(LOCAL_NAME, v8Array);
        } finally {
            v8Array.close();
        }
    }

    public ValueMirror getLocalValue(int i) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(i);
        AutoCloseable autoCloseable = null;
        try {
            V8Object executeObjectFunction = this.v8Object.executeObjectFunction(LOCAL_VALUE, v8Array);
            if (Mirror.isValue(executeObjectFunction)) {
                ValueMirror createMirror = Mirror.createMirror(executeObjectFunction);
                v8Array.close();
                if (executeObjectFunction != null) {
                    executeObjectFunction.close();
                }
                return createMirror;
            }
            throw new IllegalStateException("Local value is not a ValueMirror");
        } catch (Throwable th) {
            v8Array.close();
            if (0 != 0) {
                autoCloseable.close();
            }
            throw th;
        }
    }

    public Scope getScope(int i) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(i);
        V8Object v8Object = null;
        try {
            v8Object = this.v8Object.executeObjectFunction(SCOPE, v8Array);
            return new Scope(v8Object);
        } finally {
            v8Array.close();
            if (v8Object != null) {
                v8Object.close();
            }
        }
    }

    public int getScopeCount() {
        return this.v8Object.executeIntegerFunction(SCOPE_COUNT, null);
    }

    public SourceLocation getSourceLocation() {
        String str = null;
        V8Object executeObjectFunction = this.v8Object.executeObjectFunction(SOURCE_LOCATION, null);
        FunctionMirror function = getFunction();
        String scriptName = function.getScriptName();
        try {
            V8Object v8Object = (V8Object) executeObjectFunction.get(SCRIPT);
            if (v8Object != null) {
                str = v8Object.getString(NAME);
                v8Object.close();
            }
            if (str != null || scriptName == null) {
                scriptName = "undefined";
            }
            return new SourceLocation(scriptName, executeObjectFunction.getInteger(POSITION), executeObjectFunction.getInteger(LINE), executeObjectFunction.getInteger(COLUMN), executeObjectFunction.getString(SOURCE_TEXT));
        } finally {
            function.close();
            executeObjectFunction.close();
        }
    }

    @Override
    public boolean isFrame() {
        return true;
    }
}
