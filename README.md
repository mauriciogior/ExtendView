[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ExtendView-green.svg?style=true)](https://android-arsenal.com/details/1/4500)

# ExtendView

Have you ever wanted a more sophisticated `<include />` on your XML files?

You don't need to create a custom `ViewGroup` anymore!

To use with gradle: `compile "com.mauriciogiordano:extendview:1.0.2"`

## Example
`viewgroup_container.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#eee">

    <!-- A top border -->
    <View
        android:layout_width="match_parent"
        android:layout_height="1px"
        android:background="#ccc" />

    <!-- Our container that will hold our content -->
    <FrameLayout android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#fefefe">

    </FrameLayout>

    <!-- A bottom border -->
    <View
        android:layout_width="match_parent"
        android:layout_height="1px"
        android:background="#ccc" />

    <!-- A shadow -->
    <View android:layout_width="match_parent"
        android:layout_height="2dp"
        android:background="@drawable/tab_shadow"/>

</LinearLayout>
```

And finally, inside our layout we can use the container:

`fragment_information.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    android:id="@+id/majorLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <com.mauriciogiordano.extendview.ExtendView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="8dp"
        android:padding="50dp"
        custom:view_group_layout="@layout/viewgroup_container"
        custom:container_id="@id/container">
    
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="Hello World" />
    
    </com.mauriciogiordano.extendview.ExtendView>
    
</LinearLayout>
```

### Result

`viewgroup_container.xml` view:

![](https://i.imgur.com/G7qvNRu.png)

`fragment_information.xml` view:

![](https://i.imgur.com/RGEq7JM.png)
