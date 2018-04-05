package com.example.soleeklab.riderapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PrefsUtil {


    private static final String DEFAULT_APP_PREFS_NAME = "yamam-default-prefs";

    private static SharedPreferences getPrefs() {

        return YamamApplication.get().getSharedPreferences(DEFAULT_APP_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void saveString( String key, String value) {
        getPrefs().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getPrefs().getString(key, null);
    }


    public static void saveBoolean(String key, boolean value) {
        getPrefs().edit().putBoolean(key, value).apply();
    }


    public static boolean getBoolean(String key) {
        return getPrefs().getBoolean(key, false);
    }

    public static void storeItems(String sort, ArrayList<Location> items) {
        // used for store arrayList in json format
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(items);
        getPrefs().edit().putString(sort, jsonFavorites).apply();
    }
    public static ArrayList<Location> loadItems(String sort) {
        // used for retrieving arraylist from json formatted string
        List items = new ArrayList();
        if (getPrefs().contains(sort)) {
            String jsonFavorites = getPrefs().getString(sort, null);
            Gson gson = new Gson();
            Location[] favoriteItems = gson.fromJson(jsonFavorites,Location[].class);
            if (favoriteItems!=null){
                items = Arrays.asList(favoriteItems);
                items = new ArrayList(items);
            }
        } else
            return null;
        return (ArrayList) items;
    }

    }