package com.example.smartcity.core

class MutableData<T> {
    constructor()
    constructor(data: T)
    var value: T? = null
        @Synchronized
        set(value: T?){
            field = value
            observers.forEach { (_, u) ->
                u.onChanged(field)
            }
        }
    get(){
        return field
    }

    private val observers = HashMap<Long, Observer<T>>()



    fun observe(observer: Observer<T>){
        observers[System.currentTimeMillis()] = observer
    }

    interface Observer<T>{
        fun onChanged(data: T?)
    }
}