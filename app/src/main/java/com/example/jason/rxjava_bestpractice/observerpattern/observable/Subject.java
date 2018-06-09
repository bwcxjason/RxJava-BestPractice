package com.example.jason.rxjava_bestpractice.observerpattern.observable;

import com.example.jason.rxjava_bestpractice.observerpattern.observer.Observer;

public interface Subject {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

}
