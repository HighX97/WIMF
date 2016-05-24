package moun.com.wimf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import moun.com.wimf.model.Items;
import moun.com.wimf.model.WIMF_UserItems;
import moun.com.wimf.model.Orders;

/**
 * This Class using SQLiteDatabase object provides methods for SQLite CRUD
 * (Create, Read, Update, Delete) operations.
 */
public class WIMF_ItemsDAO extends WIMF_ItemsDBDAO {
    public static final String ITEM_ID_WITH_PREFIX = "cart.id";
    public static final String ITEM_NAME_WITH_PREFIX = "cart.name";
    public static final String ORDER_NAME_WITH_PREFIX = "orders.ordered";

    private static final String WHERE_ID_EQUALS = WIMF_DataBaseHelper.ID_COLUMN
            + " =?";

    public WIMF_ItemsDAO(Context context) {
        super(context);
    }

    public long saveToItemsTable(Items items) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.NAME_COLUMN, items.getItemName());
        values.put(WIMF_DataBaseHelper.DESCRIPTION_COLOMN, items.getItemDescription());
        values.put(WIMF_DataBaseHelper.IMAGE_COLOMN, items.getItemImage());
        values.put(WIMF_DataBaseHelper.PRICE_COLOMN, items.getItemPrice());
        values.put(WIMF_DataBaseHelper.QUANTITY_COLOMN, items.getItemQuantity());
        values.put(WIMF_DataBaseHelper.ORDER_ID, items.getOrders().getId());

        return database.insert(WIMF_DataBaseHelper.ITEMS_TABLE, null, values);
    }

    public long saveToFavoriteTable(WIMF_UserItems menuItems) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.NAME_COLUMN, menuItems.getItemName());
        values.put(WIMF_DataBaseHelper.DESCRIPTION_COLOMN, menuItems.getItemDescription());
        values.put(WIMF_DataBaseHelper.IMAGE_COLOMN, menuItems.getItemImage());
        values.put(WIMF_DataBaseHelper.PRICE_COLOMN, menuItems.getItemPrice());

        return database.insert(WIMF_DataBaseHelper.FAVORITE_TABLE, null, values);
    }

    public long updateItemsTable(Items items) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.NAME_COLUMN, items.getItemName());
        values.put(WIMF_DataBaseHelper.DESCRIPTION_COLOMN, items.getItemDescription());
        values.put(WIMF_DataBaseHelper.IMAGE_COLOMN, items.getItemImage());
        values.put(WIMF_DataBaseHelper.PRICE_COLOMN, items.getItemPrice());
        values.put(WIMF_DataBaseHelper.QUANTITY_COLOMN, items.getItemQuantity());
        values.put(WIMF_DataBaseHelper.ORDER_ID, items.getOrders().getId());

        long result = database.update(WIMF_DataBaseHelper.ITEMS_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(items.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public long updateFavoriteTable(WIMF_UserItems menuItems) {
        ContentValues values = new ContentValues();
        values.put(WIMF_DataBaseHelper.NAME_COLUMN, menuItems.getItemName());
        values.put(WIMF_DataBaseHelper.DESCRIPTION_COLOMN, menuItems.getItemDescription());
        values.put(WIMF_DataBaseHelper.IMAGE_COLOMN, menuItems.getItemImage());
        values.put(WIMF_DataBaseHelper.PRICE_COLOMN, menuItems.getItemPrice());

        long result = database.update(WIMF_DataBaseHelper.FAVORITE_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(menuItems.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int deleteFromItemsTable(Items cart) {
        return database.delete(WIMF_DataBaseHelper.ITEMS_TABLE, WHERE_ID_EQUALS,
                new String[] { cart.getId() + "" });
    }



    public int deleteFromFavorites(WIMF_UserItems menuItems) {
        return database.delete(WIMF_DataBaseHelper.FAVORITE_TABLE, WHERE_ID_EQUALS,
                new String[] { menuItems.getId() + "" });
    }

    //USING query() method
    public ArrayList<Items> getAllCartItems() {
        ArrayList<Items> cartItems = new ArrayList<Items>();

        Cursor cursor = database.query(WIMF_DataBaseHelper.ITEMS_TABLE,
                new String[] { WIMF_DataBaseHelper.ID_COLUMN,
                        WIMF_DataBaseHelper.NAME_COLUMN,
                        WIMF_DataBaseHelper.DESCRIPTION_COLOMN,
                        WIMF_DataBaseHelper.IMAGE_COLOMN,
                        WIMF_DataBaseHelper.PRICE_COLOMN,
                        WIMF_DataBaseHelper.QUANTITY_COLOMN,
                        WIMF_DataBaseHelper.ORDER_ID}, null, null, null,
                null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Items cart = new Items();
                cart.setId(cursor.getInt(0));
                cart.setItemName(cursor.getString(1));
                cart.setItemDescription(cursor.getString(2));
                cart.setItemImage(cursor.getInt(3));
                cart.setItemPrice(cursor.getDouble(4));
                cart.setItemQuantity(cursor.getInt(5));
                Orders orders = new Orders();
                orders.setId(cursor.getInt(6));
                orders.setOrdered(cursor.getInt(7)>0);
                orders.setDate_created(cursor.getLong(8));
                cart.setOrders(orders);

                cartItems.add(cart);
            }
        }
        Log.d("GET CART ITEMS:", "=" + cartItems.toString());

        return cartItems;
    }

    // Uses rawQuery() to query multiple tables
    public ArrayList<Items> getCartItemsNotOrdered() {
        ArrayList<Items> cartItems = new ArrayList<Items>();

        // Building query using INNER JOIN keyword
        String query = "SELECT " + ITEM_ID_WITH_PREFIX + ","
                + ITEM_NAME_WITH_PREFIX + "," + WIMF_DataBaseHelper.DESCRIPTION_COLOMN
                + "," + WIMF_DataBaseHelper.IMAGE_COLOMN + ","
                + WIMF_DataBaseHelper.PRICE_COLOMN + ","
                + WIMF_DataBaseHelper.QUANTITY_COLOMN + ","
                + WIMF_DataBaseHelper.ORDER_ID + ","
                + ORDER_NAME_WITH_PREFIX + " FROM "
                + WIMF_DataBaseHelper.ITEMS_TABLE + " cart INNER JOIN "
                + WIMF_DataBaseHelper.ORDERS_TABLE + " orders ON cart."
                + WIMF_DataBaseHelper.ORDER_ID + " = orders."
                + WIMF_DataBaseHelper.ID_COLUMN
                + " WHERE " + "orders." + WIMF_DataBaseHelper.ORDERED + " = ?";


        Log.d("query", query);
        Cursor cursor = database.rawQuery(query, new String[] { 0 + "" } );
        while (cursor.moveToNext()) {
            Items cart = new Items();
            cart.setId(cursor.getInt(0));
            cart.setItemName(cursor.getString(1));
            cart.setItemDescription(cursor.getString(2));
            cart.setItemImage(cursor.getInt(3));
            cart.setItemPrice(cursor.getDouble(4));
            cart.setItemQuantity(cursor.getInt(5));

            Orders orders = new Orders();
            orders.setId(cursor.getInt(6));
            orders.setOrdered(cursor.getInt(7)>0);

            cart.setOrders(orders);

            cartItems.add(cart);

        }
        Log.d("GET CART ITEMS:", "=" + cartItems);
        return cartItems;
    }

    //USING query() method
    public ArrayList<Items> getItemsOrderHistory(int id) {
        ArrayList<Items> cartItems = new ArrayList<Items>();

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.ITEMS_TABLE
                + " WHERE " + WIMF_DataBaseHelper.ORDER_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        while (cursor.moveToNext()) {
            Items cart = new Items();
            cart.setId(cursor.getInt(0));
            cart.setItemName(cursor.getString(1));
            cart.setItemDescription(cursor.getString(2));
            cart.setItemImage(cursor.getInt(3));
            cart.setItemPrice(cursor.getDouble(4));
            cart.setItemQuantity(cursor.getInt(5));

            Orders orders = new Orders();
            orders.setId(cursor.getInt(6));

            cart.setOrders(orders);

            cartItems.add(cart);

        }
        Log.d("GET CART ITEMS:", "=" + cartItems.toString());

        return cartItems;
    }

    //USING query() method
    public ArrayList<WIMF_UserItems> getFavoriteItems() {
        ArrayList<WIMF_UserItems> cartItems = new ArrayList<WIMF_UserItems>();

        Cursor cursor = database.query(WIMF_DataBaseHelper.FAVORITE_TABLE,
                new String[] { WIMF_DataBaseHelper.ID_COLUMN,
                        WIMF_DataBaseHelper.NAME_COLUMN,
                        WIMF_DataBaseHelper.DESCRIPTION_COLOMN,
                        WIMF_DataBaseHelper.IMAGE_COLOMN,
                        WIMF_DataBaseHelper.PRICE_COLOMN }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            WIMF_UserItems menuItems = new WIMF_UserItems();
            menuItems.setId(cursor.getInt(0));
            menuItems.setItemName(cursor.getString(1));
            menuItems.setItemDescription(cursor.getString(2));
            menuItems.setItemImage(cursor.getInt(3));
            menuItems.setItemPrice(cursor.getDouble(4));

            cartItems.add(menuItems);
        }
        return cartItems;
    }

    //Retrieves a single cart item record with the given id
    public WIMF_UserItems getItemCart(long id) {
        WIMF_UserItems menuItems = null;

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.ITEMS_TABLE
                + " WHERE " + WIMF_DataBaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            menuItems = new WIMF_UserItems();
            menuItems.setId(cursor.getInt(0));
            menuItems.setItemName(cursor.getString(1));
            menuItems.setItemDescription(cursor.getString(2));
            menuItems.setItemImage(cursor.getInt(3));
            menuItems.setItemPrice(cursor.getDouble(4));
            menuItems.setItemQuantity(cursor.getInt(5));
        }
        return menuItems;
    }

    //Retrieves a single favorite item record with the item name
    public WIMF_UserItems getItemFavorite(String title) {
        WIMF_UserItems menuItems = null;

        String sql = "SELECT * FROM " + WIMF_DataBaseHelper.FAVORITE_TABLE
                + " WHERE " + WIMF_DataBaseHelper.NAME_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { title + "" });

        if (cursor.moveToNext()) {
            menuItems = new WIMF_UserItems();
            menuItems.setId(cursor.getInt(0));
            menuItems.setItemName(cursor.getString(1));
            menuItems.setItemDescription(cursor.getString(2));
            menuItems.setItemImage(cursor.getInt(3));
            menuItems.setItemPrice(cursor.getDouble(4));

        }
        return menuItems;
    }

}
