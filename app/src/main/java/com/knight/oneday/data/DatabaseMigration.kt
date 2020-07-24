package com.knight.oneday.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val Migration1To2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE task ADD COLUMN need_remind INTEGER NOT NULL DEFAULT 0"
        )
    }
}