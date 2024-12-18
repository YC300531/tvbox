package com.sun.jna;

import androidx.base.a.a;
import androidx.base.a.b;
import androidx.exifinterface.media.ExifInterface;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
public abstract class Structure {
    public static final int ALIGN_DEFAULT = 0;
    public static final int ALIGN_GNUC = 2;
    public static final int ALIGN_MSVC = 3;
    public static final int ALIGN_NONE = 1;
    public static final int CALCULATE_SIZE = -1;
    private int actualAlignType;
    private int alignType;
    private Structure[] array;
    private boolean autoRead;
    private boolean autoWrite;
    private String encoding;
    private Pointer memory;
    private final Map<String, NativeStringTracking> nativeStrings;
    private boolean readCalled;
    private int size;
    private int structAlignment;
    private Map<String, StructField> structFields;
    private long typeInfo;
    private TypeMapper typeMapper;
    private static final Logger LOG = Logger.getLogger(Structure.class.getName());
    public static final Map<Class<?>, LayoutInfo> layoutInfo = new WeakHashMap();
    public static final Map<Class<?>, List<String>> fieldOrder = new WeakHashMap();
    private static final ThreadLocal<Map<Pointer, Structure>> reads = new ThreadLocal<Map<Pointer, Structure>>() {
        @Override
        public synchronized Map<Pointer, Structure> initialValue() {
            return new HashMap();
        }
    };
    private static final ThreadLocal<Set<Structure>> busy = new ThreadLocal<Set<Structure>>() {
        @Override
        public synchronized Set<Structure> initialValue() {
            return new StructureSet();
        }
    };
    private static final Pointer PLACEHOLDER_MEMORY = new Pointer(0) {
        @Override
        public Pointer share(long j, long j2) {
            return this;
        }
    };

    public static class AutoAllocated extends Memory {
        public AutoAllocated(int i) {
            super(i);
            super.clear();
        }

        @Override
        public String toString() {
            StringBuilder d = b.d("auto-");
            d.append(super.toString());
            return d.toString();
        }
    }

    public interface ByReference {
    }

    public interface ByValue {
    }

    @FieldOrder({"size", "alignment", "type", "elements"})
    public static class FFIType extends Structure {
        private static final int FFI_TYPE_STRUCT = 13;
        private static final Map<Pointer, FFIType> ffiTypeInfo;
        private static final Map<Class, FFIType> typeInfoMap = new WeakHashMap();
        private static final Map<Class, FFIType> unionHelper = new WeakHashMap();
        public short alignment;
        public Pointer elements;
        public size_t size;
        public short type;

        public static class FFITypes {
            private static Pointer ffi_type_double;
            private static Pointer ffi_type_float;
            private static Pointer ffi_type_longdouble;
            private static Pointer ffi_type_pointer;
            private static Pointer ffi_type_sint16;
            private static Pointer ffi_type_sint32;
            private static Pointer ffi_type_sint64;
            private static Pointer ffi_type_sint8;
            private static Pointer ffi_type_uint16;
            private static Pointer ffi_type_uint32;
            private static Pointer ffi_type_uint64;
            private static Pointer ffi_type_uint8;
            private static Pointer ffi_type_void;

            private FFITypes() {
            }
        }

        public static class size_t extends IntegerType {
            private static final long serialVersionUID = 1;

            public size_t() {
                this(0L);
            }

            public size_t(long j) {
                super(Native.SIZE_T_SIZE, j);
            }
        }

        static {
            HashMap hashMap = new HashMap();
            ffiTypeInfo = hashMap;
            if (Native.POINTER_SIZE == 0) {
                throw new Error("Native library not initialized");
            }
            if (FFITypes.ffi_type_void == null) {
                throw new Error("FFI types not initialized");
            }
            hashMap.put(FFITypes.ffi_type_void, Structure.newInstance(FFIType.class, FFITypes.ffi_type_void));
            hashMap.put(FFITypes.ffi_type_float, Structure.newInstance(FFIType.class, FFITypes.ffi_type_float));
            hashMap.put(FFITypes.ffi_type_double, Structure.newInstance(FFIType.class, FFITypes.ffi_type_double));
            hashMap.put(FFITypes.ffi_type_longdouble, Structure.newInstance(FFIType.class, FFITypes.ffi_type_longdouble));
            hashMap.put(FFITypes.ffi_type_uint8, Structure.newInstance(FFIType.class, FFITypes.ffi_type_uint8));
            hashMap.put(FFITypes.ffi_type_sint8, Structure.newInstance(FFIType.class, FFITypes.ffi_type_sint8));
            hashMap.put(FFITypes.ffi_type_uint16, Structure.newInstance(FFIType.class, FFITypes.ffi_type_uint16));
            hashMap.put(FFITypes.ffi_type_sint16, Structure.newInstance(FFIType.class, FFITypes.ffi_type_sint16));
            hashMap.put(FFITypes.ffi_type_uint32, Structure.newInstance(FFIType.class, FFITypes.ffi_type_uint32));
            hashMap.put(FFITypes.ffi_type_sint32, Structure.newInstance(FFIType.class, FFITypes.ffi_type_sint32));
            hashMap.put(FFITypes.ffi_type_uint64, Structure.newInstance(FFIType.class, FFITypes.ffi_type_uint64));
            hashMap.put(FFITypes.ffi_type_sint64, Structure.newInstance(FFIType.class, FFITypes.ffi_type_sint64));
            hashMap.put(FFITypes.ffi_type_pointer, Structure.newInstance(FFIType.class, FFITypes.ffi_type_pointer));
            for (FFIType fFIType : hashMap.values()) {
                fFIType.read();
            }
            Map<Class, FFIType> map = typeInfoMap;
            Class cls = Void.TYPE;
            Map<Pointer, FFIType> map2 = ffiTypeInfo;
            map.put(cls, map2.get(FFITypes.ffi_type_void));
            map.put(Void.class, map2.get(FFITypes.ffi_type_void));
            map.put(Float.TYPE, map2.get(FFITypes.ffi_type_float));
            map.put(Float.class, map2.get(FFITypes.ffi_type_float));
            map.put(Double.TYPE, map2.get(FFITypes.ffi_type_double));
            map.put(Double.class, map2.get(FFITypes.ffi_type_double));
            map.put(Long.TYPE, map2.get(FFITypes.ffi_type_sint64));
            map.put(Long.class, map2.get(FFITypes.ffi_type_sint64));
            map.put(Integer.TYPE, map2.get(FFITypes.ffi_type_sint32));
            map.put(Integer.class, map2.get(FFITypes.ffi_type_sint32));
            map.put(Short.TYPE, map2.get(FFITypes.ffi_type_sint16));
            map.put(Short.class, map2.get(FFITypes.ffi_type_sint16));
            FFIType fFIType2 = map2.get(Native.WCHAR_SIZE == 2 ? FFITypes.ffi_type_uint16 : FFITypes.ffi_type_uint32);
            map.put(Character.TYPE, fFIType2);
            map.put(Character.class, fFIType2);
            map.put(Byte.TYPE, map2.get(FFITypes.ffi_type_sint8));
            map.put(Byte.class, map2.get(FFITypes.ffi_type_sint8));
            map.put(Pointer.class, map2.get(FFITypes.ffi_type_pointer));
            map.put(String.class, map2.get(FFITypes.ffi_type_pointer));
            map.put(WString.class, map2.get(FFITypes.ffi_type_pointer));
            map.put(Boolean.TYPE, map2.get(FFITypes.ffi_type_uint32));
            map.put(Boolean.class, map2.get(FFITypes.ffi_type_uint32));
        }

        public FFIType() {
            this.type = (short) 13;
        }

        public FFIType(FFIType fFIType) {
            this.type = (short) 13;
            this.size = fFIType.size;
            this.alignment = fFIType.alignment;
            this.type = fFIType.type;
            this.elements = fFIType.elements;
        }

        public FFIType(Structure structure) {
            Pointer[] pointerArr;
            Map<Pointer, FFIType> map;
            Pointer pointer;
            int i;
            this.type = (short) 13;
            structure.ensureAllocated(true);
            int i2 = 0;
            if (structure instanceof Union) {
                FFIType fFIType = null;
                int i3 = 0;
                boolean z = false;
                for (StructField structField : structure.fields().values()) {
                    FFIType fieldTypeInfo = structure.getFieldTypeInfo(structField);
                    z = isIntegerType(fieldTypeInfo) ? true : z;
                    if (fFIType == null || i3 < (i = structField.size) || (i3 == i && Structure.class.isAssignableFrom(structField.type))) {
                        i3 = structField.size;
                        fFIType = fieldTypeInfo;
                    }
                }
                if (((Platform.isIntel() && Platform.is64Bit() && !Platform.isWindows()) || Platform.isARM()) && z && isFloatType(fFIType)) {
                    FFIType fFIType2 = new FFIType(fFIType);
                    if (fFIType2.size.intValue() == 4) {
                        map = ffiTypeInfo;
                        pointer = FFITypes.ffi_type_uint32;
                    } else {
                        if (fFIType2.size.intValue() == 8) {
                            map = ffiTypeInfo;
                            pointer = FFITypes.ffi_type_uint64;
                        }
                        fFIType2.write();
                        fFIType = fFIType2;
                    }
                    fFIType2.type = map.get(pointer).type;
                    fFIType2.write();
                    fFIType = fFIType2;
                }
                pointerArr = new Pointer[]{fFIType.getPointer(), null};
                unionHelper.put(structure.getClass(), fFIType);
            } else {
                pointerArr = new Pointer[structure.fields().size() + 1];
                for (StructField structField2 : structure.fields().values()) {
                    pointerArr[i2] = structure.getFieldTypeInfo(structField2).getPointer();
                    i2++;
                }
            }
            init(pointerArr);
            write();
        }

        public FFIType(Object obj, Class<?> cls) {
            this.type = (short) 13;
            int length = Array.getLength(obj);
            Pointer[] pointerArr = new Pointer[length + 1];
            Pointer pointer = get(null, cls.getComponentType()).getPointer();
            for (int i = 0; i < length; i++) {
                pointerArr[i] = pointer;
            }
            init(pointerArr);
            write();
        }

        public static FFIType get(Object obj) {
            return obj == null ? typeInfoMap.get(Pointer.class) : obj instanceof Class ? get(null, (Class) obj) : get(obj, obj.getClass());
        }

        public static FFIType get(Object obj, Class<?> cls) {
            ToNativeConverter toNativeConverter;
            TypeMapper typeMapper = Native.getTypeMapper(cls);
            if (typeMapper != null && (toNativeConverter = typeMapper.getToNativeConverter(cls)) != null) {
                cls = toNativeConverter.nativeType();
            }
            Map<Class, FFIType> map = typeInfoMap;
            synchronized (map) {
                FFIType fFIType = map.get(cls);
                if (fFIType != null) {
                    return fFIType;
                }
                if ((Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(cls)) || Callback.class.isAssignableFrom(cls)) {
                    map.put(cls, map.get(Pointer.class));
                    return map.get(Pointer.class);
                } else if (Structure.class.isAssignableFrom(cls)) {
                    if (obj == null) {
                        obj = Structure.newInstance(cls, Structure.PLACEHOLDER_MEMORY);
                    }
                    if (ByReference.class.isAssignableFrom(cls)) {
                        map.put(cls, map.get(Pointer.class));
                        return map.get(Pointer.class);
                    }
                    FFIType fFIType2 = new FFIType((Structure) obj);
                    map.put(cls, fFIType2);
                    return fFIType2;
                } else if (NativeMapped.class.isAssignableFrom(cls)) {
                    NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(cls);
                    return get(nativeMappedConverter.toNative(obj, new ToNativeContext()), nativeMappedConverter.nativeType());
                } else if (cls.isArray()) {
                    FFIType fFIType3 = new FFIType(obj, cls);
                    map.put(cls, fFIType3);
                    return fFIType3;
                } else {
                    throw new IllegalArgumentException("Unsupported type " + cls);
                }
            }
        }

        private void init(Pointer[] pointerArr) {
            Memory memory = new Memory(Native.POINTER_SIZE * pointerArr.length);
            this.elements = memory;
            memory.write(0L, pointerArr, 0, pointerArr.length);
            write();
        }

        private static boolean isFloatType(FFIType fFIType) {
            Pointer pointer = fFIType.getPointer();
            return pointer.equals(FFITypes.ffi_type_float) || pointer.equals(FFITypes.ffi_type_double);
        }

        private static boolean isIntegerType(FFIType fFIType) {
            Pointer pointer = fFIType.getPointer();
            return pointer.equals(FFITypes.ffi_type_uint8) || pointer.equals(FFITypes.ffi_type_sint8) || pointer.equals(FFITypes.ffi_type_uint16) || pointer.equals(FFITypes.ffi_type_sint16) || pointer.equals(FFITypes.ffi_type_uint32) || pointer.equals(FFITypes.ffi_type_sint32) || pointer.equals(FFITypes.ffi_type_uint64) || pointer.equals(FFITypes.ffi_type_sint64) || pointer.equals(FFITypes.ffi_type_pointer);
        }
    }

    @Target({ElementType.TYPE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FieldOrder {
        String[] value();
    }

    public static class LayoutInfo {
        private int alignType;
        private int alignment;
        private final Map<String, StructField> fields;
        private int size;
        private TypeMapper typeMapper;
        private boolean variable;

        private LayoutInfo() {
            this.size = -1;
            this.alignment = 1;
            this.fields = Collections.synchronizedMap(new LinkedHashMap());
            this.alignType = 0;
        }
    }

    public static class NativeStringTracking {
        private NativeString peer;
        private final Object value;

        public NativeStringTracking(Object obj) {
            this.value = obj;
        }
    }

    public static class StructField {
        public FromNativeContext context;
        public Field field;
        public boolean isReadOnly;
        public boolean isVolatile;
        public String name;
        public FromNativeConverter readConverter;
        public Class<?> type;
        public ToNativeConverter writeConverter;
        public int size = -1;
        public int offset = -1;

        public String toString() {
            return this.name + "@" + this.offset + "[" + this.size + "] (" + this.type + ")";
        }
    }

    public static class StructureSet extends AbstractCollection<Structure> implements Set<Structure> {
        private int count;
        public Structure[] elements;

        private void ensureCapacity(int i) {
            Structure[] structureArr = this.elements;
            if (structureArr == null) {
                this.elements = new Structure[(i * 3) / 2];
            } else if (structureArr.length < i) {
                Structure[] structureArr2 = new Structure[(i * 3) / 2];
                System.arraycopy(structureArr, 0, structureArr2, 0, structureArr.length);
                this.elements = structureArr2;
            }
        }

        private int indexOf(Structure structure) {
            for (int i = 0; i < this.count; i++) {
                Structure structure2 = this.elements[i];
                if (structure == structure2 || (structure.getClass() == structure2.getClass() && structure.size() == structure2.size() && structure.getPointer().equals(structure2.getPointer()))) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public boolean add(Structure structure) {
            if (contains(structure)) {
                return false;
            }
            ensureCapacity(this.count + 1);
            Structure[] structureArr = this.elements;
            int i = this.count;
            this.count = i + 1;
            structureArr[i] = structure;
            return true;
        }

        @Override
        public boolean contains(Object obj) {
            return indexOf((Structure) obj) != -1;
        }

        public Structure[] getElements() {
            return this.elements;
        }

        @Override
        public Iterator<Structure> iterator() {
            int i = this.count;
            Structure[] structureArr = new Structure[i];
            if (i > 0) {
                System.arraycopy(this.elements, 0, structureArr, 0, i);
            }
            return Arrays.asList(structureArr).iterator();
        }

        @Override
        public boolean remove(Object obj) {
            int indexOf = indexOf((Structure) obj);
            if (indexOf != -1) {
                int i = this.count - 1;
                this.count = i;
                if (i >= 0) {
                    Structure[] structureArr = this.elements;
                    structureArr[indexOf] = structureArr[i];
                    structureArr[i] = null;
                }
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return this.count;
        }
    }

    public Structure() {
        this(0);
    }

    public Structure(int i) {
        this((Pointer) null, i);
    }

    public Structure(int i, TypeMapper typeMapper) {
        this(null, i, typeMapper);
    }

    public Structure(Pointer pointer) {
        this(pointer, 0);
    }

    public Structure(Pointer pointer, int i) {
        this(pointer, i, null);
    }

    public Structure(Pointer pointer, int i, TypeMapper typeMapper) {
        this.size = -1;
        this.nativeStrings = new HashMap(8);
        this.autoRead = true;
        this.autoWrite = true;
        setAlignType(i);
        setStringEncoding(Native.getStringEncoding(getClass()));
        initializeTypeMapper(typeMapper);
        validateFields();
        if (pointer != null) {
            useMemory(pointer, 0, true);
        } else {
            allocateMemory(-1);
        }
        initializeFields();
    }

    public Structure(TypeMapper typeMapper) {
        this(null, 0, typeMapper);
    }

    private int addPadding(int i) {
        return addPadding(i, this.structAlignment);
    }

    private int addPadding(int i, int i2) {
        int i3;
        return (this.actualAlignType == 1 || (i3 = i % i2) == 0) ? i : i + (i2 - i3);
    }

    private void allocateMemory(boolean z) {
        allocateMemory(calculateSize(true, z));
    }

    public static void autoRead(Structure[] structureArr) {
        structureArrayCheck(structureArr);
        if (structureArr[0].array == structureArr) {
            structureArr[0].autoRead();
            return;
        }
        for (int i = 0; i < structureArr.length; i++) {
            if (structureArr[i] != null) {
                structureArr[i].autoRead();
            }
        }
    }

    public static void autoWrite(Structure[] structureArr) {
        structureArrayCheck(structureArr);
        if (structureArr[0].array == structureArr) {
            structureArr[0].autoWrite();
            return;
        }
        for (int i = 0; i < structureArr.length; i++) {
            if (structureArr[i] != null) {
                structureArr[i].autoWrite();
            }
        }
    }

    private Class<?> baseClass() {
        return (((this instanceof ByReference) || (this instanceof ByValue)) && Structure.class.isAssignableFrom(getClass().getSuperclass())) ? getClass().getSuperclass() : getClass();
    }

    public static Set<Structure> busy() {
        return busy.get();
    }

    public static List<String> createFieldsOrder(String str) {
        return Collections.unmodifiableList(Collections.singletonList(str));
    }

    public static List<String> createFieldsOrder(List<String> list, List<String> list2) {
        ArrayList arrayList = new ArrayList(list2.size() + list.size());
        arrayList.addAll(list);
        arrayList.addAll(list2);
        return Collections.unmodifiableList(arrayList);
    }

    public static List<String> createFieldsOrder(List<String> list, String... strArr) {
        return createFieldsOrder(list, Arrays.asList(strArr));
    }

    public static List<String> createFieldsOrder(String... strArr) {
        return Collections.unmodifiableList(Arrays.asList(strArr));
    }

    private LayoutInfo deriveLayout(boolean z, boolean z2) {
        Class<?> cls;
        int i;
        List<Field> fields = getFields(z);
        if (fields == null) {
            return null;
        }
        LayoutInfo layoutInfo2 = new LayoutInfo();
        layoutInfo2.alignType = this.alignType;
        layoutInfo2.typeMapper = this.typeMapper;
        boolean z3 = true;
        int i2 = 0;
        boolean z4 = true;
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            Class<?> type = field.getType();
            if (type.isArray()) {
                layoutInfo2.variable = z3;
            }
            StructField structField = new StructField();
            structField.isVolatile = Modifier.isVolatile(modifiers);
            boolean isFinal = Modifier.isFinal(modifiers);
            structField.isReadOnly = isFinal;
            if (isFinal) {
                if (!Platform.RO_FIELDS) {
                    StringBuilder d = b.d("This VM does not support read-only fields (field '");
                    d.append(field.getName());
                    d.append("' within ");
                    d.append(getClass());
                    d.append(")");
                    throw new IllegalArgumentException(d.toString());
                }
                field.setAccessible(z3);
            }
            structField.field = field;
            structField.name = field.getName();
            structField.type = type;
            if (Callback.class.isAssignableFrom(type) && !type.isInterface()) {
                StringBuilder d2 = b.d("Structure Callback field '");
                d2.append(field.getName());
                d2.append("' must be an interface");
                throw new IllegalArgumentException(d2.toString());
            } else if (type.isArray() && Structure.class.equals(type.getComponentType())) {
                throw new IllegalArgumentException("Nested Structure arrays must use a derived Structure type so that the size of the elements can be determined");
            } else {
                if (Modifier.isPublic(field.getModifiers())) {
                    Object fieldValue = getFieldValue(structField.field);
                    if (fieldValue == null && type.isArray()) {
                        if (z) {
                            throw new IllegalStateException("Array fields must be initialized");
                        }
                        return null;
                    }
                    if (NativeMapped.class.isAssignableFrom(type)) {
                        NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(type);
                        cls = nativeMappedConverter.nativeType();
                        structField.writeConverter = nativeMappedConverter;
                        structField.readConverter = nativeMappedConverter;
                        structField.context = new StructureReadContext(this, field);
                    } else {
                        TypeMapper typeMapper = this.typeMapper;
                        if (typeMapper != null) {
                            ToNativeConverter toNativeConverter = typeMapper.getToNativeConverter(type);
                            FromNativeConverter fromNativeConverter = this.typeMapper.getFromNativeConverter(type);
                            if (toNativeConverter != null && fromNativeConverter != null) {
                                fieldValue = toNativeConverter.toNative(fieldValue, new StructureWriteContext(this, structField.field));
                                Class cls2 = fieldValue != null ? fieldValue.getClass() : Pointer.class;
                                structField.writeConverter = toNativeConverter;
                                structField.readConverter = fromNativeConverter;
                                structField.context = new StructureReadContext(this, field);
                                cls = cls2;
                            } else if (toNativeConverter != null || fromNativeConverter != null) {
                                throw new IllegalArgumentException(androidx.base.r5.b.f("Structures require bidirectional type conversion for ", type));
                            }
                        }
                        cls = type;
                    }
                    if (fieldValue == null) {
                        fieldValue = initializeField(structField.field, type);
                    }
                    try {
                        structField.size = getNativeSize(cls, fieldValue);
                        int nativeAlignment = getNativeAlignment(cls, fieldValue, z4);
                        if (nativeAlignment == 0) {
                            StringBuilder d3 = b.d("Field alignment is zero for field '");
                            d3.append(structField.name);
                            d3.append("' within ");
                            d3.append(getClass());
                            throw new Error(d3.toString());
                        }
                        layoutInfo2.alignment = Math.max(layoutInfo2.alignment, nativeAlignment);
                        int i3 = i2 % nativeAlignment;
                        if (i3 != 0) {
                            i2 += nativeAlignment - i3;
                        }
                        if (this instanceof Union) {
                            structField.offset = 0;
                            i = Math.max(i2, structField.size);
                        } else {
                            structField.offset = i2;
                            i = structField.size + i2;
                        }
                        layoutInfo2.fields.put(structField.name, structField);
                        i2 = i;
                    } catch (IllegalArgumentException e) {
                        if (z || this.typeMapper != null) {
                            StringBuilder d4 = b.d("Invalid Structure field in ");
                            d4.append(getClass());
                            d4.append(", field name '");
                            d4.append(structField.name);
                            d4.append("' (");
                            d4.append(structField.type);
                            d4.append("): ");
                            d4.append(e.getMessage());
                            throw new IllegalArgumentException(d4.toString(), e);
                        }
                        return null;
                    }
                }
                z3 = true;
                z4 = false;
            }
        }
        if (i2 <= 0) {
            StringBuilder d5 = b.d("Structure ");
            d5.append(getClass());
            d5.append(" has unknown or zero size (ensure all fields are public)");
            throw new IllegalArgumentException(d5.toString());
        }
        int addPadding = addPadding(i2, layoutInfo2.alignment);
        if ((this instanceof ByValue) && !z2) {
            getTypeInfo();
        }
        layoutInfo2.size = addPadding;
        return layoutInfo2;
    }

    public void ensureAllocated(boolean z) {
        if (this.memory == null) {
            allocateMemory(z);
        } else if (this.size == -1) {
            int calculateSize = calculateSize(true, z);
            this.size = calculateSize;
            Pointer pointer = this.memory;
            if (pointer instanceof AutoAllocated) {
                return;
            }
            try {
                this.memory = pointer.share(0L, calculateSize);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Structure exceeds provided memory bounds", e);
            }
        }
    }

    private List<String> fieldOrder() {
        List<String> list;
        Class<?> cls = getClass();
        Map<Class<?>, List<String>> map = fieldOrder;
        synchronized (map) {
            list = map.get(cls);
            if (list == null) {
                list = getFieldOrder();
                map.put(cls, list);
            }
        }
        return list;
    }

    private String format(Class<?> cls) {
        String name = cls.getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private static <T> Constructor<T> getPointerConstructor(Class<T> cls) {
        for (Constructor<?> constructor : cls.getConstructors()) {
            Constructor<T> constructor2 = (Constructor<T>) constructor;
            Class<?>[] parameterTypes = constructor2.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].equals(Pointer.class)) {
                return constructor2;
            }
        }
        return null;
    }

    public static FFIType getTypeInfo(Object obj) {
        return FFIType.get(obj);
    }

    private Object initializeField(Field field, Class<?> cls) {
        if (!Structure.class.isAssignableFrom(cls) || ByReference.class.isAssignableFrom(cls)) {
            if (NativeMapped.class.isAssignableFrom(cls)) {
                NativeMapped defaultValue = NativeMappedConverter.getInstance(cls).defaultValue();
                setFieldValue(field, defaultValue);
                return defaultValue;
            }
            return null;
        }
        try {
            Structure newInstance = newInstance((Class<Structure>) cls, PLACEHOLDER_MEMORY);
            setFieldValue(field, newInstance);
            return newInstance;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't determine size of nested structure", e);
        }
    }

    private void initializeFields() {
        for (Field field : getFieldList()) {
            try {
                if (field.get(this) == null) {
                    initializeField(field, field.getType());
                }
            } catch (Exception e) {
                StringBuilder d = b.d("Exception reading field '");
                d.append(field.getName());
                d.append("' in ");
                d.append(getClass());
                throw new Error(d.toString(), e);
            }
        }
    }

    private void initializeTypeMapper(TypeMapper typeMapper) {
        if (typeMapper == null) {
            typeMapper = Native.getTypeMapper(getClass());
        }
        this.typeMapper = typeMapper;
        layoutChanged();
    }

    private void layoutChanged() {
        if (this.size != -1) {
            this.size = -1;
            if (this.memory instanceof AutoAllocated) {
                this.memory = null;
            }
            ensureAllocated();
        }
    }

    public static <T extends Structure> T newInstance(Class<T> cls) {
        T t = (T) Klass.newInstance(cls);
        if (t instanceof ByValue) {
            t.allocateMemory();
        }
        return t;
    }

    private static <T extends Structure> T newInstance(Class<T> cls, long j) {
        try {
            T t = (T) newInstance(cls, j == 0 ? PLACEHOLDER_MEMORY : new Pointer(j));
            if (j != 0) {
                t.conditionalAutoRead();
            }
            return t;
        } catch (Throwable th) {
            LOG.log(Level.WARNING, "JNA: Error creating structure", th);
            return null;
        }
    }

    public static <T extends Structure> T newInstance(Class<T> cls, Pointer pointer) {
        try {
            Constructor pointerConstructor = getPointerConstructor(cls);
            if (pointerConstructor != null) {
                return (T) pointerConstructor.newInstance(pointer);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(androidx.base.r5.b.g("Instantiation of ", cls, " (Pointer) not allowed, is it public?"), e);
        } catch (InstantiationException e2) {
            throw new IllegalArgumentException(androidx.base.r5.b.f("Can't instantiate ", cls), e2);
        } catch (SecurityException unused) {
        } catch (InvocationTargetException e3) {
            throw new IllegalArgumentException(androidx.base.r5.b.f("Exception thrown while instantiating an instance of ", cls), e3);
        }
        T t = (T) newInstance(cls);
        if (pointer != PLACEHOLDER_MEMORY) {
            t.useMemory(pointer);
        }
        return t;
    }

    public static Map<Pointer, Structure> reading() {
        return reads.get();
    }

    private void setFieldValue(Field field, Object obj, boolean z) {
        try {
            field.set(this, obj);
        } catch (IllegalAccessException e) {
            if (!Modifier.isFinal(field.getModifiers())) {
                StringBuilder d = b.d("Unexpectedly unable to write to field '");
                d.append(field.getName());
                d.append("' within ");
                d.append(getClass());
                throw new Error(d.toString(), e);
            } else if (!z) {
                StringBuilder d2 = b.d("Attempt to write to read-only field '");
                d2.append(field.getName());
                d2.append("' within ");
                d2.append(getClass());
                throw new UnsupportedOperationException(d2.toString(), e);
            } else {
                StringBuilder d3 = b.d("This VM does not support Structures with final fields (field '");
                d3.append(field.getName());
                d3.append("' within ");
                d3.append(getClass());
                d3.append(")");
                throw new UnsupportedOperationException(d3.toString(), e);
            }
        }
    }

    public static int size(Class<? extends Structure> cls) {
        return size(cls, null);
    }

    public static <T extends Structure> int size(Class<T> cls, T t) {
        LayoutInfo layoutInfo2;
        Map<Class<?>, LayoutInfo> map = layoutInfo;
        synchronized (map) {
            layoutInfo2 = map.get(cls);
        }
        int i = (layoutInfo2 == null || layoutInfo2.variable) ? -1 : layoutInfo2.size;
        if (i == -1) {
            if (t == null) {
                t = (T) newInstance(cls, PLACEHOLDER_MEMORY);
            }
            return t.size();
        }
        return i;
    }

    private static <T extends Comparable<T>> List<T> sort(Collection<? extends T> collection) {
        ArrayList arrayList = new ArrayList(collection);
        Collections.sort(arrayList);
        return arrayList;
    }

    private static void structureArrayCheck(Structure[] structureArr) {
        if (ByReference[].class.isAssignableFrom(structureArr.getClass())) {
            return;
        }
        Pointer pointer = structureArr[0].getPointer();
        int size = structureArr[0].size();
        for (int i = 1; i < structureArr.length; i++) {
            if (structureArr[i].getPointer().peer != pointer.peer + (size * i)) {
                throw new IllegalArgumentException(b.a("Structure array elements must use contiguous memory (bad backing address at Structure array index ", i, ")"));
            }
        }
    }

    private String toString(int i, boolean z, boolean z2) {
        String str;
        String str2;
        StringBuilder d;
        String format;
        ensureAllocated();
        String property = System.getProperty("line.separator");
        String str3 = format(getClass()) + "(" + getPointer() + ")";
        if (!(getPointer() instanceof Memory)) {
            StringBuilder e = b.e(str3, " (");
            e.append(size());
            e.append(" bytes)");
            str3 = e.toString();
        }
        String str4 = "";
        for (int i2 = 0; i2 < i; i2++) {
            str4 = b.b(str4, "  ");
        }
        if (z) {
            Iterator<StructField> it = fields().values().iterator();
            String str5 = property;
            while (it.hasNext()) {
                StructField next = it.next();
                Object fieldValue = getFieldValue(next.field);
                String format2 = format(next.type);
                String b = b.b(str5, str4);
                if (!next.type.isArray() || fieldValue == null) {
                    str2 = "";
                } else {
                    format2 = format(next.type.getComponentType());
                    StringBuilder d2 = b.d("[");
                    d2.append(Array.getLength(fieldValue));
                    d2.append("]");
                    str2 = d2.toString();
                }
                StringBuilder d3 = b.d(b);
                d3.append(String.format("  %s %s%s@0x%X", format2, next.name, str2, Integer.valueOf(next.offset)));
                String sb = d3.toString();
                if (fieldValue instanceof Structure) {
                    fieldValue = ((Structure) fieldValue).toString(i + 1, !(fieldValue instanceof ByReference), z2);
                }
                String b2 = b.b(sb, "=");
                if (fieldValue instanceof Long) {
                    d = b.d(b2);
                    format = String.format("0x%08X", (Long) fieldValue);
                } else if (fieldValue instanceof Integer) {
                    d = b.d(b2);
                    format = String.format("0x%04X", (Integer) fieldValue);
                } else if (fieldValue instanceof Short) {
                    d = b.d(b2);
                    format = String.format("0x%02X", (Short) fieldValue);
                } else {
                    boolean z3 = fieldValue instanceof Byte;
                    d = b.d(b2);
                    format = z3 ? String.format("0x%01X", (Byte) fieldValue) : String.valueOf(fieldValue).trim();
                }
                d.append(format);
                str5 = b.b(d.toString(), property);
                if (!it.hasNext()) {
                    str5 = a.j(str5, str4, "}");
                }
            }
            str = str5;
        } else {
            str = "...}";
        }
        if (i == 0 && z2) {
            String str6 = str + property + "memory dump" + property;
            byte[] byteArray = getPointer().getByteArray(0L, size());
            for (int i3 = 0; i3 < byteArray.length; i3++) {
                int i4 = i3 % 4;
                if (i4 == 0) {
                    str6 = b.b(str6, "[");
                }
                if (byteArray[i3] >= 0 && byteArray[i3] < 16) {
                    str6 = b.b(str6, SessionDescription.SUPPORTED_SDP_VERSION);
                }
                StringBuilder d4 = b.d(str6);
                d4.append(Integer.toHexString(byteArray[i3] & ExifInterface.MARKER));
                str6 = d4.toString();
                if (i4 == 3 && i3 < byteArray.length - 1) {
                    str6 = a.j(str6, "]", property);
                }
            }
            str = b.b(str6, "]");
        }
        return a.j(str3, " {", str);
    }

    public static <T extends Structure> T updateStructureByReference(Class<T> cls, T t, Pointer pointer) {
        if (pointer == null) {
            return null;
        }
        if ((t != null && pointer.equals(t.getPointer())) || ((t = (T) reading().get(pointer)) != null && cls.equals(t.getClass()))) {
            t.autoRead();
            return t;
        }
        T t2 = (T) newInstance(cls, pointer);
        t2.conditionalAutoRead();
        return t2;
    }

    public static void validate(Class<? extends Structure> cls) {
        try {
            cls.getConstructor(new Class[0]);
        } catch (NoSuchMethodException | SecurityException unused) {
            StringBuilder d = b.d("No suitable constructor found for class: ");
            d.append(cls.getName());
            throw new IllegalArgumentException(d.toString());
        }
    }

    private void validateField(String str, Class<?> cls) {
        ToNativeConverter toNativeConverter;
        TypeMapper typeMapper = this.typeMapper;
        if (typeMapper != null && (toNativeConverter = typeMapper.getToNativeConverter(cls)) != null) {
            validateField(str, toNativeConverter.nativeType());
        } else if (cls.isArray()) {
            validateField(str, cls.getComponentType());
        } else {
            try {
                getNativeSize(cls);
            } catch (IllegalArgumentException e) {
                StringBuilder d = b.d("Invalid Structure field in ");
                d.append(getClass());
                d.append(", field name '");
                d.append(str);
                d.append("' (");
                d.append(cls);
                d.append("): ");
                d.append(e.getMessage());
                throw new IllegalArgumentException(d.toString(), e);
            }
        }
    }

    private void validateFields() {
        for (Field field : getFieldList()) {
            validateField(field.getName(), field.getType());
        }
    }

    private void writeField(StructField structField, Object obj) {
        int i = structField.offset;
        Class<?> cls = structField.type;
        ToNativeConverter toNativeConverter = structField.writeConverter;
        if (toNativeConverter != null) {
            obj = toNativeConverter.toNative(obj, new StructureWriteContext(this, structField.field));
            cls = toNativeConverter.nativeType();
        }
        if (String.class == cls || WString.class == cls) {
            if (obj != null) {
                NativeStringTracking nativeStringTracking = new NativeStringTracking(obj);
                NativeStringTracking put = this.nativeStrings.put(structField.name, nativeStringTracking);
                if (put != null && obj.equals(put.value)) {
                    nativeStringTracking.peer = put.peer;
                    return;
                }
                NativeString nativeString = cls == WString.class ? new NativeString(obj.toString(), true) : new NativeString(obj.toString(), this.encoding);
                nativeStringTracking.peer = nativeString;
                obj = nativeString.getPointer();
            } else {
                this.nativeStrings.remove(structField.name);
            }
        }
        try {
            this.memory.setValue(i, obj, cls);
        } catch (IllegalArgumentException e) {
            StringBuilder d = b.d("Structure field \"");
            d.append(structField.name);
            d.append("\" was declared as ");
            d.append(structField.type);
            throw new IllegalArgumentException(b.c(d, structField.type == cls ? "" : androidx.base.r5.b.g(" (native type ", cls, ")"), ", which is not supported within a Structure"), e);
        }
    }

    public void allocateMemory() {
        allocateMemory(false);
    }

    public void allocateMemory(int i) {
        if (i == -1) {
            i = calculateSize(false);
        } else if (i <= 0) {
            throw new IllegalArgumentException(a.h("Structure size must be greater than zero: ", i));
        }
        if (i != -1) {
            Pointer pointer = this.memory;
            if (pointer == null || (pointer instanceof AutoAllocated)) {
                this.memory = autoAllocate(i);
            }
            this.size = i;
        }
    }

    public Memory autoAllocate(int i) {
        return new AutoAllocated(i);
    }

    public void autoRead() {
        if (!getAutoRead()) {
            return;
        }
        read();
        if (this.array == null) {
            return;
        }
        int i = 1;
        while (true) {
            Structure[] structureArr = this.array;
            if (i >= structureArr.length) {
                return;
            }
            structureArr[i].autoRead();
            i++;
        }
    }

    public void autoWrite() {
        if (!getAutoWrite()) {
            return;
        }
        write();
        if (this.array == null) {
            return;
        }
        int i = 1;
        while (true) {
            Structure[] structureArr = this.array;
            if (i >= structureArr.length) {
                return;
            }
            structureArr[i].autoWrite();
            i++;
        }
    }

    public void cacheTypeInfo(Pointer pointer) {
        this.typeInfo = pointer.peer;
    }

    public int calculateSize(boolean z) {
        return calculateSize(z, false);
    }

    public int calculateSize(boolean z, boolean z2) {
        LayoutInfo layoutInfo2;
        Class<?> cls = getClass();
        Map<Class<?>, LayoutInfo> map = layoutInfo;
        synchronized (map) {
            layoutInfo2 = map.get(cls);
        }
        if (layoutInfo2 == null || this.alignType != layoutInfo2.alignType || this.typeMapper != layoutInfo2.typeMapper) {
            layoutInfo2 = deriveLayout(z, z2);
        }
        if (layoutInfo2 != null) {
            this.structAlignment = layoutInfo2.alignment;
            this.structFields = layoutInfo2.fields;
            if (!layoutInfo2.variable) {
                synchronized (map) {
                    if (!map.containsKey(cls) || this.alignType != 0 || this.typeMapper != null) {
                        map.put(cls, layoutInfo2);
                    }
                }
            }
            return layoutInfo2.size;
        }
        return -1;
    }

    public void clear() {
        ensureAllocated();
        this.nativeStrings.clear();
        this.memory.clear(size());
    }

    public void conditionalAutoRead() {
        if (this.readCalled) {
            return;
        }
        autoRead();
    }

    public boolean dataEquals(Structure structure) {
        return dataEquals(structure, false);
    }

    public boolean dataEquals(Structure structure, boolean z) {
        if (z) {
            structure.getPointer().clear(structure.size());
            structure.write();
            getPointer().clear(size());
            write();
        }
        byte[] byteArray = structure.getPointer().getByteArray(0L, structure.size());
        byte[] byteArray2 = getPointer().getByteArray(0L, size());
        if (byteArray.length == byteArray2.length) {
            for (int i = 0; i < byteArray.length; i++) {
                if (byteArray[i] != byteArray2[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void ensureAllocated() {
        ensureAllocated(false);
    }

    public boolean equals(Object obj) {
        return (obj instanceof Structure) && obj.getClass() == getClass() && ((Structure) obj).getPointer().equals(getPointer());
    }

    public int fieldOffset(String str) {
        ensureAllocated();
        StructField structField = fields().get(str);
        if (structField != null) {
            return structField.offset;
        }
        throw new IllegalArgumentException(b.b("No such field: ", str));
    }

    public Map<String, StructField> fields() {
        return this.structFields;
    }

    public boolean getAutoRead() {
        return this.autoRead;
    }

    public boolean getAutoWrite() {
        return this.autoWrite;
    }

    public List<Field> getFieldList() {
        ArrayList arrayList = new ArrayList();
        for (Class<?> cls = getClass(); !cls.equals(Structure.class); cls = cls.getSuperclass()) {
            ArrayList arrayList2 = new ArrayList();
            Field[] declaredFields = cls.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                int modifiers = declaredFields[i].getModifiers();
                if (!Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                    arrayList2.add(declaredFields[i]);
                }
            }
            arrayList.addAll(0, arrayList2);
        }
        return arrayList;
    }

    public List<String> getFieldOrder() {
        LinkedList linkedList = new LinkedList();
        for (Class<?> cls = getClass(); cls != Structure.class; cls = cls.getSuperclass()) {
            FieldOrder fieldOrder2 = (FieldOrder) cls.getAnnotation(FieldOrder.class);
            if (fieldOrder2 != null) {
                linkedList.addAll(0, Arrays.asList(fieldOrder2.value()));
            }
        }
        return Collections.unmodifiableList(linkedList);
    }

    public FFIType getFieldTypeInfo(StructField structField) {
        ToNativeConverter toNativeConverter;
        Class<?> cls = structField.type;
        Object fieldValue = getFieldValue(structField.field);
        TypeMapper typeMapper = this.typeMapper;
        if (typeMapper != null && (toNativeConverter = typeMapper.getToNativeConverter(cls)) != null) {
            cls = toNativeConverter.nativeType();
            fieldValue = toNativeConverter.toNative(fieldValue, new ToNativeContext());
        }
        return FFIType.get(fieldValue, cls);
    }

    public Object getFieldValue(Field field) {
        try {
            return field.get(this);
        } catch (Exception e) {
            StringBuilder d = b.d("Exception reading field '");
            d.append(field.getName());
            d.append("' in ");
            d.append(getClass());
            throw new Error(d.toString(), e);
        }
    }

    public List<Field> getFields(boolean z) {
        List<Field> fieldList = getFieldList();
        HashSet hashSet = new HashSet();
        for (Field field : fieldList) {
            hashSet.add(field.getName());
        }
        List<String> fieldOrder2 = fieldOrder();
        if (fieldOrder2.size() == fieldList.size() || fieldList.size() <= 1) {
            if (new HashSet(fieldOrder2).equals(hashSet)) {
                sortFields(fieldList, fieldOrder2);
                return fieldList;
            }
            StringBuilder d = b.d("Structure.getFieldOrder() on ");
            d.append(getClass());
            d.append(" returns names (");
            d.append(sort(fieldOrder2));
            d.append(") which do not match declared field names (");
            d.append(sort(hashSet));
            d.append(")");
            throw new Error(d.toString());
        } else if (z) {
            StringBuilder d2 = b.d("Structure.getFieldOrder() on ");
            d2.append(getClass());
            d2.append(fieldOrder2.size() < fieldList.size() ? " does not provide enough" : " provides too many");
            d2.append(" names [");
            d2.append(fieldOrder2.size());
            d2.append("] (");
            d2.append(sort(fieldOrder2));
            d2.append(") to match declared fields [");
            d2.append(fieldList.size());
            d2.append("] (");
            d2.append(sort(hashSet));
            d2.append(")");
            throw new Error(d2.toString());
        } else {
            return null;
        }
    }

    public int getNativeAlignment(Class<?> cls, Object obj, boolean z) {
        if (NativeMapped.class.isAssignableFrom(cls)) {
            NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(cls);
            Class<?> nativeType = nativeMappedConverter.nativeType();
            obj = nativeMappedConverter.toNative(obj, new ToNativeContext());
            cls = nativeType;
        }
        int nativeSize = Native.getNativeSize(cls, obj);
        if (!cls.isPrimitive() && Long.class != cls && Integer.class != cls && Short.class != cls && Character.class != cls && Byte.class != cls && Boolean.class != cls && Float.class != cls && Double.class != cls) {
            if ((!Pointer.class.isAssignableFrom(cls) || Function.class.isAssignableFrom(cls)) && ((!Platform.HAS_BUFFERS || !Buffer.class.isAssignableFrom(cls)) && !Callback.class.isAssignableFrom(cls) && WString.class != cls && String.class != cls)) {
                if (Structure.class.isAssignableFrom(cls)) {
                    if (!ByReference.class.isAssignableFrom(cls)) {
                        if (obj == null) {
                            obj = newInstance(cls, PLACEHOLDER_MEMORY);
                        }
                        nativeSize = ((Structure) obj).getStructAlignment();
                    }
                } else if (!cls.isArray()) {
                    throw new IllegalArgumentException(androidx.base.r5.b.g("Type ", cls, " has unknown native alignment"));
                } else {
                    nativeSize = getNativeAlignment(cls.getComponentType(), null, z);
                }
            }
            nativeSize = Native.POINTER_SIZE;
        }
        int i = this.actualAlignType;
        if (i == 1) {
            return 1;
        }
        if (i == 3) {
            return Math.min(8, nativeSize);
        }
        if (i == 2) {
            if (!z || !Platform.isMac() || !Platform.isPPC()) {
                nativeSize = Math.min(Native.MAX_ALIGNMENT, nativeSize);
            }
            if (z || !Platform.isAIX()) {
                return nativeSize;
            }
            if (cls == Double.TYPE || cls == Double.class) {
                return 4;
            }
            return nativeSize;
        }
        return nativeSize;
    }

    public int getNativeSize(Class<?> cls) {
        return getNativeSize(cls, null);
    }

    public int getNativeSize(Class<?> cls, Object obj) {
        return Native.getNativeSize(cls, obj);
    }

    public Pointer getPointer() {
        ensureAllocated();
        return this.memory;
    }

    public String getStringEncoding() {
        return this.encoding;
    }

    public int getStructAlignment() {
        if (this.size == -1) {
            calculateSize(true);
        }
        return this.structAlignment;
    }

    public Pointer getTypeInfo() {
        Pointer pointer = getTypeInfo(this).getPointer();
        cacheTypeInfo(pointer);
        return pointer;
    }

    public TypeMapper getTypeMapper() {
        return this.typeMapper;
    }

    public int hashCode() {
        return getPointer() != null ? getPointer().hashCode() : getClass().hashCode();
    }

    public void read() {
        if (this.memory == PLACEHOLDER_MEMORY) {
            return;
        }
        this.readCalled = true;
        ensureAllocated();
        if (busy().add(this)) {
            boolean z = this instanceof ByReference;
            if (z) {
                reading().put(getPointer(), this);
            }
            try {
                for (StructField structField : fields().values()) {
                    readField(structField);
                }
            } finally {
                busy().remove(this);
                if (z && reading().get(getPointer()) == this) {
                    reading().remove(getPointer());
                }
            }
        }
    }

    public Object readField(StructField structField) {
        int i = structField.offset;
        Class<?> cls = structField.type;
        FromNativeConverter fromNativeConverter = structField.readConverter;
        if (fromNativeConverter != null) {
            cls = fromNativeConverter.nativeType();
        }
        Object obj = null;
        Object fieldValue = (Structure.class.isAssignableFrom(cls) || Callback.class.isAssignableFrom(cls) || (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(cls)) || Pointer.class.isAssignableFrom(cls) || NativeMapped.class.isAssignableFrom(cls) || cls.isArray()) ? getFieldValue(structField.field) : null;
        if (cls == String.class) {
            Pointer pointer = this.memory.getPointer(i);
            if (pointer != null) {
                obj = pointer.getString(0L, this.encoding);
            }
        } else {
            obj = this.memory.getValue(i, cls, fieldValue);
        }
        if (fromNativeConverter != null) {
            Object fromNative = fromNativeConverter.fromNative(obj, structField.context);
            if (fieldValue == null || !fieldValue.equals(fromNative)) {
                fieldValue = fromNative;
            }
        } else {
            fieldValue = obj;
        }
        if (cls.equals(String.class) || cls.equals(WString.class)) {
            if (fieldValue != null) {
                NativeStringTracking nativeStringTracking = new NativeStringTracking(fieldValue);
                NativeStringTracking put = this.nativeStrings.put(structField.name, nativeStringTracking);
                if (put != null) {
                    nativeStringTracking.peer = put.peer;
                }
            } else {
                this.nativeStrings.remove(structField.name);
            }
        }
        setFieldValue(structField.field, fieldValue, true);
        return fieldValue;
    }

    public Object readField(String str) {
        ensureAllocated();
        StructField structField = fields().get(str);
        if (structField != null) {
            return readField(structField);
        }
        throw new IllegalArgumentException(b.b("No such field: ", str));
    }

    public void setAlignType(int i) {
        this.alignType = i;
        if (i == 0 && (i = Native.getStructureAlignment(getClass())) == 0) {
            i = Platform.isWindows() ? 3 : 2;
        }
        this.actualAlignType = i;
        layoutChanged();
    }

    public void setAutoRead(boolean z) {
        this.autoRead = z;
    }

    public void setAutoSynch(boolean z) {
        setAutoRead(z);
        setAutoWrite(z);
    }

    public void setAutoWrite(boolean z) {
        this.autoWrite = z;
    }

    public void setFieldValue(Field field, Object obj) {
        setFieldValue(field, obj, false);
    }

    public void setStringEncoding(String str) {
        this.encoding = str;
    }

    public int size() {
        ensureAllocated();
        return this.size;
    }

    public void sortFields(List<Field> list, List<String> list2) {
        for (int i = 0; i < list2.size(); i++) {
            String str = list2.get(i);
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    break;
                } else if (str.equals(list.get(i2).getName())) {
                    Collections.swap(list, i, i2);
                    break;
                } else {
                    i2++;
                }
            }
        }
    }

    public Structure[] toArray(int i) {
        return toArray((Structure[]) Array.newInstance(getClass(), i));
    }

    public Structure[] toArray(Structure[] structureArr) {
        ensureAllocated();
        Pointer pointer = this.memory;
        if (pointer instanceof AutoAllocated) {
            int size = size() * structureArr.length;
            if (((Memory) pointer).size() < size) {
                useMemory(autoAllocate(size));
            }
        }
        structureArr[0] = this;
        int size2 = size();
        for (int i = 1; i < structureArr.length; i++) {
            structureArr[i] = newInstance((Class<Structure>) getClass(), this.memory.share(i * size2, size2));
            structureArr[i].conditionalAutoRead();
        }
        if (!(this instanceof ByValue)) {
            this.array = structureArr;
        }
        return structureArr;
    }

    public String toString() {
        return toString(Boolean.getBoolean("jna.dump_memory"));
    }

    public String toString(boolean z) {
        return toString(0, true, z);
    }

    public void useMemory(Pointer pointer) {
        useMemory(pointer, 0);
    }

    public void useMemory(Pointer pointer, int i) {
        useMemory(pointer, i, false);
    }

    public void useMemory(Pointer pointer, int i, boolean z) {
        try {
            this.nativeStrings.clear();
            if (!(this instanceof ByValue) || z) {
                if (this.size == -1) {
                    this.size = calculateSize(false);
                }
                int i2 = this.size;
                this.memory = i2 != -1 ? pointer.share(i, i2) : pointer.share(i);
            } else {
                int size = size();
                byte[] bArr = new byte[size];
                pointer.read(0L, bArr, 0, size);
                this.memory.write(0L, bArr, 0, size);
            }
            this.array = null;
            this.readCalled = false;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Structure exceeds provided memory bounds", e);
        }
    }

    public void write() {
        if (this.memory == PLACEHOLDER_MEMORY) {
            return;
        }
        ensureAllocated();
        if (this instanceof ByValue) {
            getTypeInfo();
        }
        if (busy().add(this)) {
            try {
                for (StructField structField : fields().values()) {
                    if (!structField.isVolatile) {
                        writeField(structField);
                    }
                }
            } finally {
                busy().remove(this);
            }
        }
    }

    public void writeField(StructField structField) {
        if (structField.isReadOnly) {
            return;
        }
        writeField(structField, getFieldValue(structField.field));
    }

    public void writeField(String str) {
        ensureAllocated();
        StructField structField = fields().get(str);
        if (structField == null) {
            throw new IllegalArgumentException(b.b("No such field: ", str));
        }
        writeField(structField);
    }

    public void writeField(String str, Object obj) {
        ensureAllocated();
        StructField structField = fields().get(str);
        if (structField == null) {
            throw new IllegalArgumentException(b.b("No such field: ", str));
        }
        setFieldValue(structField.field, obj);
        writeField(structField, obj);
    }
}
