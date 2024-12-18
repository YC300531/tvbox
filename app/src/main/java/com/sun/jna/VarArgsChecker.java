package com.sun.jna;

import java.lang.reflect.Method;
abstract class VarArgsChecker {

    public static class fun1 {
    }

    public static final class NoVarArgsChecker extends VarArgsChecker {
        private NoVarArgsChecker() {
            super(null);
        }

        public NoVarArgsChecker(1 r1) {
            this();
        }

        @Override
        public int fixedArgs(Method method) {
            return 0;
        }

        @Override
        public boolean isVarArgs(Method method) {
            return false;
        }
    }

    public static final class RealVarArgsChecker extends VarArgsChecker {
        private RealVarArgsChecker() {
            super(null);
        }

        public RealVarArgsChecker(1 r1) {
            this();
        }

        @Override
        public int fixedArgs(Method method) {
            if (method.isVarArgs()) {
                return method.getParameterTypes().length - 1;
            }
            return 0;
        }

        @Override
        public boolean isVarArgs(Method method) {
            return method.isVarArgs();
        }
    }

    private VarArgsChecker() {
    }

    public VarArgsChecker(1 r1) {
        this();
    }

    public static VarArgsChecker create() {
        try {
            return Method.class.getMethod("isVarArgs", new Class[0]) != null ? new RealVarArgsChecker(null) : new NoVarArgsChecker(null);
        } catch (NoSuchMethodException unused) {
            return new NoVarArgsChecker(null);
        } catch (SecurityException unused2) {
            return new NoVarArgsChecker(null);
        }
    }

    public abstract int fixedArgs(Method method);

    public abstract boolean isVarArgs(Method method);
}
