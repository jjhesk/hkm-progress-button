# hkm-progress-button
Base on android-process-button this is the advanced version of the android-process-button.
## Description 
[![Maven Central](https://img.shields.io/github/tag/jjhesk/hkm-progress-button.svg?label=maven)](https://jitpack.io/#jjhesk/hkm-progress-button) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Process%20Button-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1691)
[![gitpay](http://fc07.deviantart.net/fs70/f/2012/336/f/9/little_pixel_heart_by_tiny_bear-d5mtwiu.gif)](https://gratipay.com/jjhesk/) [release log](https://github.com/jjhesk/hkm-progress-button/releases)

Android Buttons With Built-in Progress Meters.

![](screenshots/sample1_small1.gif)
![](screenshots/sample1_small2.gif)

### Wiki

- [Home]
- [Screenshots]
- [User Guide]

## Code Sample
```java

        add_bag = (ActionProcessButton) findViewById(R.id.add_to_bag);
        
        add_bag.setCompleteText(act.getResources().getString(R.string.button_3));
        
        add_bag.setText(act.getResources().getString(R.string.button_1));
        // set the progress mode on endless
        add_bag.setMode(ActionProcessButton.Mode.ENDLESS);
        // add success state color scheme as a button and not a sign
        add_bag.setOnCompleteColorButton(R.color.green_800, R.color.green_900);
        // add listener for click on the button for success state
        add_bag.setOnClickCompleteState(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(context, CartWebViewStyleTopBar.class);
                context.startActivity(h);
            }
        });
        
```
### Integration

The lib is available on maven jitpack, so you will need to add a class path for custom repos
```
	dependencies {
	        compile 'com.hkm:hkmprocessbuttons:1.0.5:aar'
	}
```

### Sample

<a href="https://play.google.com/store/apps/details?id=com.dd.sample.processbutton">
  <img alt="Android app on Google Play"
       src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>
<a href="https://play.google.com/store/apps/details?id=com.inappsquared.devappsdirect">
  <img alt="DevAppsDirect"
       src="http://www.inappsquared.com/img/icons/devappsdirect_icon.png" width="48" height="48" />
</a>
