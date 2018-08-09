package com.fightbackfoods.adapter;

public interface OnItemClick<T> {
    void onItemClick(T t, int position, long id);
}
