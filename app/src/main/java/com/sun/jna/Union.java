package com.sun.jna;

import androidx.base.a.b;
import com.sun.jna.Structure;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
public abstract class Union extends Structure {
    private Structure.StructField activeField;

    public Union() {
    }

    public Union(Pointer pointer) {
        super(pointer);
    }

    public Union(Pointer pointer, int i) {
        super(pointer, i);
    }

    public Union(Pointer pointer, int i, TypeMapper typeMapper) {
        super(pointer, i, typeMapper);
    }

    public Union(TypeMapper typeMapper) {
        super(typeMapper);
    }

    private Structure.StructField findField(Class<?> cls) {
        ensureAllocated();
        for (Structure.StructField structField : fields().values()) {
            if (structField.type.isAssignableFrom(cls)) {
                return structField;
            }
        }
        return null;
    }

    @Override
    public List<String> getFieldOrder() {
        List<Field> fieldList = getFieldList();
        ArrayList arrayList = new ArrayList(fieldList.size());
        for (Field field : fieldList) {
            arrayList.add(field.getName());
        }
        return arrayList;
    }

    @Override
    public int getNativeAlignment(Class<?> cls, Object obj, boolean z) {
        return super.getNativeAlignment(cls, obj, true);
    }

    public Object getTypedValue(Class<?> cls) {
        ensureAllocated();
        for (Structure.StructField structField : fields().values()) {
            if (structField.type == cls) {
                this.activeField = structField;
                read();
                return getFieldValue(this.activeField.field);
            }
        }
        throw new IllegalArgumentException("No field of type " + cls + " in " + this);
    }

    @Override
    public Object readField(Structure.StructField structField) {
        if (structField == this.activeField || !(Structure.class.isAssignableFrom(structField.type) || String.class.isAssignableFrom(structField.type) || WString.class.isAssignableFrom(structField.type))) {
            return super.readField(structField);
        }
        return null;
    }

    @Override
    public Object readField(String str) {
        ensureAllocated();
        setType(str);
        return super.readField(str);
    }

    public void setType(Class<?> cls) {
        ensureAllocated();
        for (Structure.StructField structField : fields().values()) {
            if (structField.type == cls) {
                this.activeField = structField;
                return;
            }
        }
        throw new IllegalArgumentException("No field of type " + cls + " in " + this);
    }

    public void setType(String str) {
        ensureAllocated();
        Structure.StructField structField = fields().get(str);
        if (structField != null) {
            this.activeField = structField;
            return;
        }
        throw new IllegalArgumentException("No field named " + str + " in " + this);
    }

    public Object setTypedValue(Object obj) {
        Structure.StructField findField = findField(obj.getClass());
        if (findField != null) {
            this.activeField = findField;
            setFieldValue(findField.field, obj);
            return this;
        }
        StringBuilder d = b.d("No field of type ");
        d.append(obj.getClass());
        d.append(" in ");
        d.append(this);
        throw new IllegalArgumentException(d.toString());
    }

    @Override
    public void writeField(Structure.StructField structField) {
        if (structField == this.activeField) {
            super.writeField(structField);
        }
    }

    @Override
    public void writeField(String str) {
        ensureAllocated();
        setType(str);
        super.writeField(str);
    }

    @Override
    public void writeField(String str, Object obj) {
        ensureAllocated();
        setType(str);
        super.writeField(str, obj);
    }
}
