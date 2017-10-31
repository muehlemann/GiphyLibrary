# GiphyLibrary

Created on Oct. 7th, 2017 by Matt Muehlemann

## Screenshots
![GiphyLibrary](https://user-images.githubusercontent.com/4277927/32251016-7f6da556-be65-11e7-8bfa-03877cd24c70.png)

## Dependency
Add dependency to build.gradle of your app

```java
dependencies {
    implementation 'com.muehlemann:giphy:1.0.1'
}
```

## Usage
Add the GiphyLibrary activity to your projects android manifest
```xml
    <activity 
        android:name="com.muehlemann.giphy.GiphyActivity"
        android:theme="@style/Theme.AppCompat.Light.NoActionBar" />
```

Create a GiphyLibrary object in your activity.
```java
GiphyLibrary giphyLibrary = new GiphyLibrary();
```

To start the GiphyLibrary picker call:
```java
giphyLibrary.start(context, listener, API_KEY);
```

Then delegate the onActivityResult to the GiphyLibrary object and implement the GiphyLibrary listener.
```java
public class MainActivity implements GiphyLibrary.Listener {
        
    ...

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        giphyLibrary.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onGiphySelected(String url) {
        Glide.with(MainActivity.this).load(url).into(imageView);
    }

}
```

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details