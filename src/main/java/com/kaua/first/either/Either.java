package com.kaua.first.either;

public abstract class Either<A, B>{

    private static class Base<A, B> extends Either<A, B> {
        @Override
        public boolean leftValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public A getLeftValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Right rightValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public B getRightValue() {
            throw new UnsupportedOperationException();
        }
    }

    public abstract boolean leftValue();

    public abstract A getLeftValue();

    public abstract B getRightValue();

    public abstract Right rightValue();

    public static <A, B> Either<A, B> left(A value) {
        return new Base<A, B>().new Left(value);
    }

    public static <A, B> Either<A, B> right(B value) {
        return new Base<A, B>().new Right(value);
    }

    public class Left extends Either<A, B> {

        private A value;

        public Left(A value) {
            this.value = value;
        }

        @Override
        public boolean leftValue() {
            return true;
        }

        @Override
        public A getLeftValue() {
            return this.value;
        }

        @Override
        public Right rightValue() {
            return null;
        }

        @Override
        public B getRightValue() {
            return null;
        }

    }

    public class Right extends Either<A, B> {

        private B value;

        public Right(B value) {
            this.value = value;
        }

        @Override
        public boolean leftValue() {
            return false;
        }

        public A getLeftValue() {
            return null;
        }

        @Override
        public B getRightValue() {
            return this.value;
        }

        @Override
        public Right rightValue() {
            return this;
        }
    }
}