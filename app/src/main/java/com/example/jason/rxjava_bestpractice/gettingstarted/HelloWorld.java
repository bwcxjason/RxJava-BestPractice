package com.example.jason.rxjava_bestpractice.gettingstarted;


import android.annotation.SuppressLint;

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
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

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


    @SuppressLint("CheckResult")
    private static void doOnEach() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("hello");
                        emitter.onNext("world");
                        emitter.onComplete();
                    }
                })
                .doOnEach(integerNotification -> System.out.println("doOnEach:" + integerNotification.getValue()))
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void doOnNext() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("hello");
                        emitter.onNext("world");
                        emitter.onComplete();
                    }
                })
                .doOnNext(s -> System.out.println("doOnNext:" + s))
                .subscribe(System.out::println);
    }


    private static void doOnLifecycle() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("hello");
                        emitter.onNext("world");
                        emitter.onComplete();
                    }
                })
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("doOnLifecycle accept");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doOnLifecycle action");
                    }
                })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doOnDispose action");
                    }
                })
                .subscribe(new Observer<String>() {
                    private Disposable d;

                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                        this.d = d;
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

    private static int j = 0;

    private static void retryUntil() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onError(new Exception("404"));
                    }
                })
                .retryUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        if (j > 10) {
                            return true;
                        }
                        return false;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        j += integer;
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


    private static void retryWhen() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("hello");
                        emitter.onNext("world");
                        emitter.onNext("my");
                        emitter.onError(new Exception("fatal"));
                        emitter.onNext("dear");
                    }
                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                if (!"java.lang.Exception: fatal".equals(throwable.toString())) {
                                    return Observable.just("可以忽略的异常");
                                } else {
                                    return Observable.error(new Throwable("终止啦"));
                                }
                            }
                        });
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
                        System.out.println("onError" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private static void subscribeOn() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        System.out.println("currentThread name: " + Thread.currentThread().getName());
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
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

    private static void observeOn() {
        Observable
                .just(1, 2, 3)
                //.observeOn(Schedulers.newThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        System.out.println("flatMap Thread name: " + Thread.currentThread().getName());
                        return Observable.just("hello: " + integer);
                    }
                })
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext Thread name: " + Thread.currentThread().getName());
                        System.out.println("onNext: " + s);
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

    private static void filter() {
        Observable
                .just(1, 2, 3)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 2;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
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
    private static void ofType() {
        Observable
                .just(1, 2, 3, "hello", "world")
                .ofType(Integer.class)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void skip() {
        Observable
                .just(1, 2, 3, 4, 5, 6, 7, 8)
                .skip(2)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void distinct() {
        Observable
                .just(1, 2, 3, 3, 2, 1)
                .distinct()
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void distinctUntilChanged() {
        Observable
                .just(1, 2, 3, 3, 3, 3, 2, 1)
                .distinctUntilChanged()
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void take() {
        Observable
                .just(1, 2, 3, 4, 5)
                .take(3)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void debounce() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        Thread.sleep(1000);
                        emitter.onNext(2);
                        Thread.sleep(1000);
                        emitter.onNext(3);
                    }
                })
                .debounce(900, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void elementAt() {
        Observable
                .just(1, 2, 3, 4)
                .elementAt(2)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void elementAtOrError() {
        Observable
                .just(1, 2, 3, 4)
                .elementAtOrError(5)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void all() {
        Observable
                .just(1, 2, 3, 4)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 0;
                    }
                })
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void takeWhile() {
        Observable
                .just(1, 2, 3, 4)
                .takeWhile(integer -> integer < 3)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void skipWhile() {
        Observable
                .just(1, 2, 3, 4, 5)
                .skipWhile(integer -> integer < 3)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void skipUntil() {
        Observable
                .intervalRange(1, 5, 0, 1, TimeUnit.SECONDS)
                .skipUntil(Observable.just(1).delay(1, TimeUnit.SECONDS))
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void sequenceEqual() {
        Observable
                .sequenceEqual(Observable.just(1, 2, 3), Observable.just(1, 3, 2))
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void contains() {
        Observable
                .just(1, 2, 3)
                .contains(3)
                .subscribe(System.out::println);
    }

    @SuppressLint("CheckResult")
    private static void amb() {
        List<Observable<Long>> list = new ArrayList<>();
        list.add(Observable.intervalRange(1, 5, 2, 1, TimeUnit.SECONDS));
        list.add(Observable.intervalRange(6, 5, 0, 1, TimeUnit.SECONDS));

        Observable
                .amb(list)
                .subscribe(System.out::println);

    }

    @SuppressLint("CheckResult")
    private static void defaultIfEmpty() {
        Observable
                .empty()
                .defaultIfEmpty("hello world")
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
//        Observable observable = createObservable(); // 创建被观察者
//        Observer observer = createObserver(); // 创建观察者
//        observable.subscribe(observer); // 订阅

        // 创建一步到位
//        create();
//
//        fromArray(); // 和 just() 类似，只不过 fromArray 可以传入多于10个的变量，并且可以传入一个数组。
//        fromCallable(); // 这里的 Callable 是 java.util.concurrent 中的 Callable，Callable 和 Runnable 的用法基本一致，只是它会返回一个结果值，这个结果值就是发给观察者的。
//        fromFuture(); // 参数中的 Future 是 java.util.concurrent 中的 Future，Future 的作用是增加了 cancel() 等方法操作 Callable，它可以通过 get() 方法来获取 Callable 返回的值。
//        fromIterable(); // 直接发送一个 List 集合数据给观察者
//
//        defer(); // 这个方法的作用就是直到被观察者被订阅后才会创建被观察者。
//
//        timer(); // 当到指定时间后就会发送一个 0L 的值给观察者。
//        interval(); // 每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字。
//        intervalRange(); // 可以指定发送事件的开始值和数量，其他与 interval() 的功能一样。
//        range(); // 同时发送一定范围的事件序列。
//
//        empty(); // 直接发送 onComplete() 事件
//        never(); // 不发送任何事件
//        error(); // 发送 onError() 事件
//
//        map(); // map 可以将被观察者发送的数据类型转变成其他的类型
//        flatMap(); // 这个方法可以将事件序列中的元素进行整合加工，返回一个新的被观察者。
//        concatMap(); // concatMap() 和 flatMap() 基本上是一样的，只不过 concatMap() 转发出来的事件是有序的，而 flatMap() 是无序的。

//        buffer(); // 从需要发送的事件当中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出。

//        groupBy(); // 将发送的数据进行分组，每个分组都会返回一个被观察者。

//        scan(); // 将数据以一定的逻辑聚合起来。

//        window(); // 发送指定数量的事件时，就将这些事件分为一组。window 中的 count 的参数就是代表指定的数量，例如将 count 指定为2，那么每发2个数据就会将这2个数据分成一组。

//        concat(); // 可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat() 最多只可以发送4个事件。

//        concatArray(); // 与 concat() 作用一样，不过 concatArray() 可以发送多于 4 个被观察者。

//        merge(); // 这个方法月 concat() 作用基本一样，知识 concat() 是串行发送事件，而 merge() 并行发送事件。

//        concatArrayDelayError(); // mergeArrayDelayError();在 concatArray() 和 mergeArray() 两个方法当中，如果其中有一个被观察者发送了一个 Error 事件，那么就会停止发送事件，如果你想 onError() 事件延迟到所有被观察者都发送完事件后再执行的话，就可以使用  concatArrayDelayError() 和 mergeArrayDelayError()

//        zip(); // 会将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，最终发送的事件数量会与源 Observable 中最少事件的数量一样。

//        combineLatest(); // combineLatest() 的作用与 zip() 类似，但是 combineLatest() 发送事件的序列是与发送的时间线有关的，当 combineLatest() 中所有的 Observable 都发送了事件，只要其中有一个 Observable 发送事件，这个事件就会和其他 Observable 最近发送的事件结合起来发送

//        reduce(); // 与 scan() 操作符的作用也是将发送数据以一定逻辑聚合起来，这两个的区别在于 scan() 每处理一次数据就会将事件发送给观察者，而 reduce() 会将所有数据聚合在一起才会发送事件给观察者。

//        collect(); // 将数据收集到数据结构当中。

//        startWith(); // 在发送事件之前追加事件，startWith() 追加一个事件，startWithArray() 可以追加多个事件。追加的事件会先发出。

//        count(); // 返回被观察者发送事件的数量。

//        delay(); // 延迟一段时间发送事件。

//        doOnEach(); // Observable 每发送一件事件之前都会先回调这个方法。
//        doOnNext(); // Observable 每发送 onNext() 之前都会先回调这个方法。
//        doAfterNext(); // Observable 每发送 onNext() 之后都会回调这个方法。
//        doOnComplete(); // Observable 每发送 onComplete() 之前都会回调这个方法。
//        doOnError(); // Observable 每发送 onError() 之前都会回调这个方法。
//        doOnSubscribe(); // Observable 每发送 onSubscribe() 之前都会回调这个方法。
//        doOnDispose(); // 当调用 Disposable 的 dispose() 之后回调该方法。
//        doOnLifecycle(); // 在回调 onSubscribe 之前回调该方法的第一个参数的回调方法，可以使用该回调方法决定是否取消订阅。
//        doOnTerminate(); // doOnTerminate 是在 onError 或者 onComplete 发送之前回调
//        doAfterTerminate(); // 而 doAfterTerminate 则是 onError 或者 onComplete 发送之后回调。
//        doFinally(); // doFinally() 无论怎么样都会被回调，且都会在事件序列的最后。

//        onErrorReturn(); // 当接受到一个 onError() 事件之后回调，返回的值会回调 onNext() 方法
//        onErrorResumeNext(); // 当接收到 onError() 事件时，返回一个新的 Observable
//        onExceptionResumeNext(); // 与 onErrorResumeNext() 作用基本一致，但是这个方法只能捕捉 Exception

//        retry(); // 如果出现错误事件，则会重新发送所有事件序列。times 是代表重新发的次数
//        retryUntil(); // 出现错误事件之后，可以通过此方法判断是否继续发送事件
//        retryWhen(); // 当被观察者接收到异常或者错误事件时会回调该方法，这个方法会返回一个新的被观察者。
                       // 如果返回的被观察者发送 Error 事件则之前的被观察者不会继续发送事件，
                       // 如果发送正常事件则之前的被观察者会继续不断重试发送事件。

//        repeat(); // 重复发送被观察者的事件，times 为发送次数
//        repeatWhen(); // 如果新的被观察者返回 onComplete 或者 onError 事件，则旧的被观察者不会继续发送事件。
                        // 如果被观察者返回其他事件，则会重复发送事件。
//        subscribeOn(); // 指定被观察者的线程，要注意的时，如果多次调用此方法，只有第一次有效
//        observeOn(); // 指定观察者的线程，每指定一次就会生效一次

//        filter(); // 通过一定逻辑来过滤被观察者发送的事件，如果返回 true 则会发送事件，否则不会发送。
//        ofType(); // 可以过滤不符合该类型事件
//        skip(); // 跳过正序某些事件，count 代表跳过事件的数量;skipLast();跳过正序的后面的事件
//        distinct(); // 过滤事件序列中的重复事件。
//        distinctUntilChanged(); // 过滤掉连续重复的事件
//        take(); // 观察者取前几个事件; takeLast()：观察者取后几个事件;
//        debounce(); // 如果两件事件发送的时间间隔小于设定的时间间隔则前一件事件就不会发送给观察者。;throttleWithTimeout：一样的用法
//        firstElement(); // 取事件序列的第一个元素
//        lastElement(); // 取事件序列的最后一个元素
//        elementAt(); // elementAt() 可以指定取出事件序列中事件，但是输入的 index 超出事件序列的总数的话就不会出现任何结果。这种情况下，你想发出异常信息的话就用 elementAtOrError() 。
//        elementAtOrError();

//        all(); // 判断事件序列是否全部满足某个事件，如果都满足则返回 true，反之则返回 false
//        takeWhile(); // 当某个数据满足条件时就会发送该数据，反之则不发送
//        skipWhile(); // 可以设置条件，当某个数据满足条件时不发送该数据，反之则发送。
//        takeUtil(); // 可以设置条件，当事件满足此条件时，下一次的事件就不会被发送了。
//        skipUntil(); // 当 skipUntil() 中的 Observable 发送事件了，原来的 Observable 才会发送事件给观察者。
//        sequenceEqual(); // 判断两个 Observable 发送的事件是否相同。
//        contains(); // 判断事件序列中是否含有某个元素，如果有则返回 true，如果没有则返回 false。
//        isEmpty(); // 判断事件序列是否为空。
//        amb(); // amb();要传入一个 Observable 集合，但是只会发送最先发送事件的 Observable 中的事件，其余 Observable 将会被丢弃。
//        defaultIfEmpty(); // 如果观察者只发送一个 onComplete() 事件，则可以利用这个方法发送一个值。
    }

}
