# GiphyLibrary

Created on Oct. 7th, 2017 by Matt Muehlemann

## Screenshots
> screenshots comming soon

## Dependency
Add dependency to build.gradle of your app

```java
dependencies {
    implementation 'com.muehlemann:giphy:1.0.1'
}
```

## Usage
Create a GiphyLibrary object in your activity.
```java
    GiphyLibrary giphyLibrary = new GiphyLibrary();
```

To start the GiphyLibrary picker call:
```java
    giphyLib.start(context, listener, API_KEY);
```

Then delegate the activity result to the giphy library
```java
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        giphyLib.onActivityResult(requestCode, resultCode, data);
    }
```

And your activity implement GiphyLibrary.Listener
```java
public class MainActivity implements GiphyLibrary.Listener {
        
    ...

    @Override
    public void onGiphySelected(String url) {
        Glide.with(MainActivity.this).load(url).into(imageView);
    }
}
```
