package com.example.jason.rxjava_bestpractice.gettingstarted;


import android.annotation.SuppressLint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

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

    private static void create() {
        Observable.just("1", "2", "3", "Hello", "World").subscribe(new Observer<String>() {
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
        });
    }

    private static void fromArray() {
        Integer[] array = {1, 2, 3, 4};
        Observable
                .fromArray(array)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private static void fromCallable() {
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello world!";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    @SuppressLint("CheckResult")
    private static void fromFuture() {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Future World!";
            }
        });

        Observable.fromFuture(futureTask).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                futureTask.run();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    @SuppressLint("CheckResult")
    private static void fromIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);

        Observable
                .fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static Integer i;

    private static void defer() {
        i = 100;
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        i = 200;

        Observer observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext:" + integer);
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

        observable.subscribe(observer);

        i = 300;

        observable.subscribe(observer);
    }

    private static void timer() {
        Observable
                .timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext:" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static void interval() {
        Observable
                .interval(4, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext:" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static void range() {
        Observable
                .range(2, 15)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static void empty() {
        Observable
                .empty()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static void never() {
        Observable
                .never()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static void error() {
        Observable
                .error(new NullPointerException())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static void map() {
        Observable
                .just(1, 2, 3, 4)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "Hello " + integer;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static List<Person> personList = new ArrayList<>();

    private static void initPersonList() {
        List<Plan> planList1 = new ArrayList<>();

        Plan planA = new Plan("PlanA", "Kill lucy");
        List<String> planAActionList = new ArrayList<>();
        planAActionList.add("Kill lucy with a knife");
        planAActionList.add("Kill lucy with a gun");
        planA.setActionList(planAActionList);

        Plan planB = new Plan("PlanB", "Save lucy");
        List<String> planBActionList = new ArrayList<>();
        planBActionList.add("Save lucy with a knife");
        planBActionList.add("Save lucy with a gun");
        planB.setActionList(planBActionList);

        planList1.add(planA);
        planList1.add(planB);

        Person lily = new Person("lily", planList1);

        List<Plan> planList2 = new ArrayList<>();

        Plan planC = new Plan("PlanC", "Kill david");
        List<String> planCActionList = new ArrayList<>();
        planCActionList.add("Kill david with a knife");
        planCActionList.add("Kill david with a gun");
        planC.setActionList(planCActionList);

        Plan planD = new Plan("PlanD", "Save david");
        List<String> planDActionList = new ArrayList<>();
        planDActionList.add("Save david with a knife");
        planDActionList.add("Save david with a gun");

        planList2.add(planC);
        planList2.add(planD);
        Person john = new Person("john", planList2);


        personList.add(lily);
        personList.add(john);
    }

    @SuppressLint("CheckResult")
    private static void flatMap() {
        initPersonList();

        // map
        Observable
                .fromIterable(personList)
                .map(new Function<Person, List<Plan>>() {
                    @Override
                    public List<Plan> apply(Person person) throws Exception {
                        return person.getPlanList();
                    }
                })
                .subscribe(new Observer<List<Plan>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(List<Plan> plans) {
                        for (Plan plan : plans) {
                            List<String> actionList = plan.getActionList();
                            for (String action : actionList) {
                                System.out.println("action:" + action);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        // flatMap
        Observable
                .fromIterable(personList)
                .map(Person::getPlanList)
                .flatMap(planList -> Observable.fromIterable(planList))
                .map(Plan::getActionList)
                .flatMap(actionList -> Observable.fromIterable(actionList))
                .subscribe(System.out::println);

        Observable
                .fromIterable(personList)
                .flatMap(person -> Observable.fromIterable(person.getPlanList()))
                .flatMap(plan -> Observable.fromIterable(plan.getActionList()))
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void concatMap() {
        initPersonList();

//        Observable
//                .fromIterable(personList)
//                .flatMap(new Function<Person, ObservableSource<Plan>>() {
//                    @Override
//                    public ObservableSource<Plan> apply(Person person) throws Exception {
//                        if ("lily".equals(person.getName())) {
//                            return Observable.fromIterable(person.getPlanList()).delay(10, TimeUnit.SECONDS);
//                        }
//                        return Observable.fromIterable(person.getPlanList());
//                    }
//                })
//                .subscribe(plan -> System.out.println(plan.getContent()));

        Observable
                .fromIterable(personList)
                .concatMap(new Function<Person, ObservableSource<Plan>>() {
                    @Override
                    public ObservableSource<Plan> apply(Person person) throws Exception {
                        if ("lily".equals(person.getName())) {
                            return Observable.fromIterable(person.getPlanList()).delay(10, TimeUnit.SECONDS);
                        }
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .subscribe(plan -> System.out.println(plan.getContent()));
        System.out.println();

    }

    private static void buffer() {
        Observable
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .buffer(2, 3)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        System.out.println("buffer size:" + integers.size());
                        for (Integer i : integers) {
                            System.out.println("list item:" + i);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private static void groupBy() {
        Observable
                .just(5, 2, 3, 4, 1, 6, 8, 9, 7, 10)
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer % 3;
                    }
                })
                .subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                        integerIntegerGroupedObservable
                                .subscribe(integer -> System.out.println("group name:" + integerIntegerGroupedObservable.getKey() + ";value:" + integer));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private static void scan() {
        Observable
                .just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });

    }

    private static void window() {
        Observable
                .just(1, 2, 3, 4, 5)
                .window(3)
                .subscribe(new Observer<Observable<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Observable<Integer> integerObservable) {
                        integerObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                System.out.println("integerObservableOnSubscribe");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                System.out.println("integerObservableOnNext:" + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("integerObservableOnError");
                            }

                            @Override
                            public void onComplete() {
                                System.out.println("integerObservableOnComplete");
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private static void concat() {
        Observable
                .concat(Observable.just(1, 2),
                        Observable.just(3, 4),
                        Observable.just(5, 6),
                        Observable.just(7, 8))
                .subscribe(System.out::println);
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    private static void concatArray() {
        Observable
                .concatArray(Observable.just(1, 2),
                        Observable.just(3, 4),
                        Observable.just(5, 6),
                        Observable.just(7, 8),
                        Observable.just(9, 10))
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void merge() {
        Observable
                .merge(Observable.interval(1, TimeUnit.SECONDS).map(aLong -> "A" + aLong),
                        Observable.interval(1, TimeUnit.SECONDS).map(aLong -> "B" + aLong))
                .subscribe(System.out::println);
        System.out.println();
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    private static void concatArrayDelayError() {
//        Observable
//                .concatArray(Observable.create(new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                        emitter.onNext("hello");
//                        emitter.onError(new NullPointerException());
//                    }
//                }), Observable.just("world"))
//                .subscribe(System.out::println);

        Observable
                .concatArrayDelayError(Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("hello");
                        emitter.onError(new NullPointerException());
                    }
                }), Observable.just("world"))
                .subscribe(System.out::println);
    }

    private static void zip() {
        Observable.zip(
                Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s1 = "A" + aLong;
                                System.out.println("A发送的事件" + s1);
                                return s1;
                            }
                        }),
                Observable.intervalRange(1, 6, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s2 = "B" + aLong;
                                System.out.println("B发送的事件" + s2);
                                return s2;
                            }
                        }),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        String res = s + s2;
                        return res;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

    }

    private static void combineLatest() {
        Observable.combineLatest(
                Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s1 = "A" + aLong;
                                System.out.println("A发送的事件" + s1);
                                return s1;
                            }
                        }),
                Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s2 = "B" + aLong;
                                System.out.println("B发送的事件" + s2);
                                return s2;
                            }
                        }),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        String res = s + s2;
                        return res;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("最终接收到的事件" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

    }

    @SuppressLint("CheckResult")
    private static void reduce() {
        Observable
                .just(0, 1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(System.out::println);

    }

    @SuppressLint("CheckResult")
    private static void collect() {
        Observable
                .just(1, 2, 3, 4)
                .collect(new Callable<ArrayList<Integer>>() {
                             @Override
                             public ArrayList<Integer> call() throws Exception {
                                 return new ArrayList<>();
                             }
                         },
                        new BiConsumer<ArrayList<Integer>, Integer>() {
                            @Override
                            public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                                integers.add(integer);
                            }
                        })
                .subscribe(System.out::println);

    }

    @SuppressLint("CheckResult")
    private static void startWith() {
        Observable
                .just(5, 6, 7)
                .startWithArray(2, 3, 4)
                .startWith(1)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void count() {
        Observable
                .just("hello", "world")
                .count()
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void delay() {
        Observable
                .just("hello", "world")
                .delay(2, TimeUnit.SECONDS)
                .subscribe(System.out::println);
    }



    public static void main(String[] args) {
//        Observable observable = createObservable();
//        Observer observer = createObserver();
//        observable.subscribe(observer);

        // or
//        create();
//
//        fromArray();
//        fromCallable();
//        fromFuture();
//        fromIterable();
//
//        defer();
//
//        timer();
//        interval();
//
//        range();
//
//        empty();
//        never();
//        error();
//
//        map();
//        flatMap();
//        concatMap();

//        buffer();

//        groupBy();

//        scan();

//        window();

//        concat();

//        concatArray();

//        merge();

//        concatArrayDelayError();

//        zip();

//        combineLatest();

//        reduce();

//        collect();

//        startWith();

//        count();

        delay();
    }

}
