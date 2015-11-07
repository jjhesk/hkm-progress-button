# Rock Button
[ ![Download](https://api.bintray.com/packages/jjhesk/maven/hkmprocessbuttons/images/download.svg) ](https://bintray.com/jjhesk/maven/hkmprocessbuttons/_latestVersion)[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-hkm--progress--button-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1691)[![gitpay](http://fc07.deviantart.net/fs70/f/2012/336/f/9/little_pixel_heart_by_tiny_bear-d5mtwiu.gif)](https://gratipay.com/jjhesk/) [release log](https://github.com/jjhesk/hkm-progress-button/releases)[![Stories in Ready](https://badge.waffle.io/jjhesk/hkm-progress-button.png?label=ready&title=Ready)](https://waffle.io/jjhesk/hkm-progress-button)

Base on android-process-button this is the advanced version of the android-process-button
[![Circle CI](https://circleci.com/gh/jjhesk/hkm-progress-button/tree/master.svg?style=svg)](https://circleci.com/gh/jjhesk/hkm-progress-button/tree/master)

##Main Features
- [x] ActionProcessButton
- [x] ArrowButton
- [x] GenerateProcessButton
- [x] SubmitProcessButton
- [x] AnimationProcessButton

Android Buttons With Built-in Progress Meters.
![](screenshots/sample1_small1.gif)
![](screenshots/sample1_small2.gif)
![](screenshots/new_sample.png)
![](screenshots/diagram-v-0-0-2.png)

### Read more with the development guide in Wiki
- [Home](https://github.com/jjhesk/hkm-progress-button/wiki)
- [Support XML attributes](https://github.com/jjhesk/hkm-progress-button/blob/master/library/src/main/res/values/styles.xml)

### Attributes

There are several attributes you can set:

| attr | description| button module base |
| :---- | :---- | :---- |
| pb_textProgress| the text content when the process is proceeding | ProcessButton |
| pb_textComplete| the complete text |ProcessButton |
| pb_textError| error message on the button | ProcessButton |
| pb_colorProgress| the color resource reference on process |ProcessButton|
| pb_colorComplete| the color resource reference on process completed|ProcessButton |
| pb_colorError| the color resource reference when its error |ProcessButton |
| pb_colorPressed| as read | FlatButton |
| pb_colorNormal|  as read | FlatButton |
| pb_colorDisabled|  as read | FlatButton |
| pb_colorBorder| as read | FlatButton |
| pb_borderWidth| as read | FlatButton |
| pb_cornerRadius| as read | FlatButton |
| pb_presentation| as read there are two options: pad_button, flat_button | FlatButton |
| pb_colorArrow| color reference to tint the arrow | ArrowButton |
| pb_arrowRes| the drawable reference for the button | ArrowButton |
| pb_verticalPadding|dimension reference padding between top and bottom on the icon on right side | ArrowButton |
| pb_topLineThickness| dimension reference for the line thickness on the top | ArrowButton |
| pb_bottomLineThickness| dimension reference for the line thickness on the bottom | ArrowButton |

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
        }).build();
        
```
### Integration

The lib is available on maven jitpack, so you will need to add a class path for custom repos

```gradle

repositories {maven { url "http://dl.bintray.com/jjhesk/maven" }}
dependencies {compile 'com.hkm:hkmprocessbuttons:1.2.4'}
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

### Status

[![Throughput Graph](https://graphs.waffle.io/jjhesk/hkm-progress-button/throughput.svg)](https://waffle.io/jjhesk/hkm-progress-button/metrics)
