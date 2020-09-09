//package com.openclassrooms.realestatemanager.provider
//
//import android.content.ContentProvider
//import android.content.ContentUris
//import android.content.ContentValues
//import android.database.Cursor
//import android.net.Uri
//import com.openclassrooms.realestatemanager.database.AppDatabase
//import com.openclassrooms.realestatemanager.model.RealEstate
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers.IO
//import kotlinx.coroutines.launch
//
//
//class RealEstateContentProvider : ContentProvider() {
//
//
//    companion object {
//
//        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
//        val TABLE_NAME = RealEstate::class.java.simpleName
//        val URI = Uri.parse("content://$AUTHORITY/$TABLE_NAME")!!
//    }
//
//
//    override fun onCreate(): Boolean {
//
//        return true
//    }
//
//
//    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
//                       selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
//
//        if (context != null) {
//            val cursor: Cursor = AppDatabase.getInstance(context!!)!!.realEstateDao().getRealEstatesWithCursor()
//            cursor.setNotificationUri(context!!.contentResolver, uri)
//            return cursor
//        }
//
//        throw IllegalArgumentException("Failed to query row for uri $uri")
//    }
//
//
//    override fun getType(uri: Uri): String? {
//
//        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
//    }
//
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//
//        return if (context != null) {
//
//            CoroutineScope(IO).launch {
//
//                val id = AppDatabase.getInstance(context!!)!!.realEstateDao()
//                        .createRealEstate(RealEstate.fromContentValues(values!!))
//
//                if (id != 0L) {
//
//                    context!!.contentResolver.notifyChange(uri, null)
//                    ContentUris.withAppendedId(uri, id)
//                }
//            }
//
//        }
//
//        throw IllegalArgumentException("Failed to insert row into $uri")
//    }
//
//
//    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
//
//        if (context != null) {
//
//            val count = AppDatabase.getInstance(context!!)!!.realEstateDao().updateRealEstate(RealEstate.fromContentValues(values!!))
//
//            context!!.contentResolver.notifyChange(uri, null)
//            return count
//        }
//
//        throw IllegalArgumentException("Failed to update row into $uri")
//    }
//
//
//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
//
//        if (context != null) {
//            val count = AppDatabase.getInstance(context!!)!!.realEstateDao().deleteRealEstate(ContentUris.parseId(uri))
//
//            context!!.contentResolver.notifyChange(uri, null)
//            return count
//        }
//
//        throw IllegalArgumentException("Failed to delete row into $uri")
//    }
//}
//
//
//
