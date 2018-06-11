package com.example.jason.rxjava_bestpractice.gettingstarted;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HelloWorld {

    private static Observable<String> createObservable() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onNext("Hello");
                emitter.onNext("World");
                emitter.onComplete();
            }
        });

        return observable;
    }

    private static Observer<String> createObserver() {
        Observer<String> observer = new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                if ("3".equals(s)) {
                    disposable.dispose();
                    return;
                }
                System.out.println("onNext:" + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        return observer;
    }

    public static void main(String[] args) {
        Observable observable = createObservable();
        Observer observer = createObserver();
        observable.subscribe(observer);
    }

}
