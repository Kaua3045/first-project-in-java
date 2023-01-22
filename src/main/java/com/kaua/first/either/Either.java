package com.kaua.first.either;

public abstract class Either<L, R>{

    private static class Base<L, R> extends Either<L, R> {
        @Override
        public boolean isLeft() {
            throw new UnsupportedOperationException();
        }

        @Override
        public L getLeftValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Right rightValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public R getRightValue() {
            throw new UnsupportedOperationException();
        }
    }

    public abstract boolean isLeft();

    public abstract L getLeftValue();

    public abstract R getRightValue();

    public abstract Right rightValue();

    public static <L, R> Either<L, R> left(L value) {
        return new Base<L, R>().new Left(value);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Base<L, R>().new Right(value);
    }

    public class Left extends Either<L, R> {

        private L value;

        public Left(L value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public L getLeftValue() {
            return this.value;
        }

        @Override
        public Right rightValue() {
            return null;
        }

        @Override
        public R getRightValue() {
            return null;
        }

    }

    public class Right extends Either<L, R> {

        private R value;

        public Right(R value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        public L getLeftValue() {
            return null;
        }

        @Override
        public R getRightValue() {
            return this.value;
        }

        @Override
        public Right rightValue() {
            return this;
        }
    }
}