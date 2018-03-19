Runtime Permission
===================

[![CircleCI](https://circleci.com/gh/florent37/RuntimePermission/tree/master.svg?style=svg)](https://circleci.com/gh/florent37/RuntimePermission/tree/master)
[![Language](https://img.shields.io/badge/compatible-java%20%7C%20kotlin%20%7C%20rx-brightgreen.svg)](https://www.github.com/florent37/RuntimePermission)

[![screen](https://raw.githubusercontent.com/florent37/RuntimePermission/master/medias/intro.png)](https://www.github.com/florent37/RuntimePermission)

**Work in progress**

Simpliest way to ask runtime permissions on Android, choose your way : 
- [Kotlin](https://github.com/florent37/RuntimePermission#kotlin)
- [Kotlin with Coroutines](https://github.com/florent37/RuntimePermission#kotlin-coroutines)
- [RxJava](https://github.com/florent37/RuntimePermission#rxjava)
- [Java8](https://github.com/florent37/RuntimePermission#java8)
- [Java7](https://github.com/florent37/RuntimePermission#java7)

**No need to override Activity or Fragment**`onPermissionResult(code, permissions, result)`**using this library, you just have to executue RuntimePermission's methods** 
This will not cut your code flow

# General Usage (cross language)

[ ![Download](https://api.bintray.com/packages/florent37/maven/runtime-permission/images/download.svg) ](https://bintray.com/florent37/maven/runtime-permission/)
```java
dependencies {
    implementation 'com.github.florent37:runtime-permission:(lastest version)'
}
```


## Detect Permissions

RuntimePermission can automatically check **all** of your needed permissions

For example, if you add to your *AndroidManifest.xml* :

[![screen](https://raw.githubusercontent.com/florent37/RuntimePermission/master/medias/manifest-permissions.png)](https://www.github.com/florent37/RuntimePermission)

You can use `askPermission` without specifying any permission

For example, in Kotlin: 
```
askPermission(){
   //all of your permissions have been accepted by the user
}.onDeclined { e -> 
   //at least one permission have been declined by the user 
}
```

[![screen](https://raw.githubusercontent.com/florent37/RuntimePermission/master/medias/permissions.png)](https://www.github.com/florent37/RuntimePermission)

Will automatically ask for **CONTACTS** and **LOCALISATION** permissions

## Manually call permissions

You just have to call `askPermission` with the list of wanted permissions

In Kotlin: 
```
askPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION){
   //all of your permissions have been accepted by the user
}.onDeclined { e -> 
   //at least one permission have been declined by the user 
}
```

[![screen](https://raw.githubusercontent.com/florent37/RuntimePermission/master/medias/permissions.png)](https://www.github.com/florent37/RuntimePermission)

Will ask for **CONTACTS** and **LOCALISATION** permissions

# Kotlin-Coroutines

```kotlin
launch(UI) {
   try {
       val result = askPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
       //all permissions already granted or just granted
       
       your action
   } catch (e: PermissionException) {
       //the list of denied permissions
       e.denied.forEach { permission ->
      
       }
       //but you can ask them again, eg:

       /*
        AlertDialog.Builder(this@MainActivityKotlinCoroutine )
               .setMessage("Please accept our permissions")
               .setPositiveButton("yes", { dialog, which -> ask again })
               .setNegativeButton("no", { dialog, which -> dialog.dismiss(); })
               .show();
       */

       //the list of forever denied permissions, user has check 'never ask again'
       e.foreverDenied.forEach { permission ->
       
       }
       //you need to open setting manually if you really need it
       //e.goToSettings();
   }
}
```

### Download 

[ ![Download](https://api.bintray.com/packages/florent37/maven/runtime-permission/images/download.svg) ](https://bintray.com/florent37/maven/runtime-permission/)
```groovy
implementation 'com.github.florent37:runtime-permission-kotlin:(last version)'
```

# Kotlin

```kotlin
askPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION){
   //all permissions already granted or just granted
  
   your action
}.onDeclined { e ->
   //the list of denied permissions
   e.denied.forEach{
       
   }
   /*
   AlertDialog.Builder(this@MainActivityKotlinCoroutine)
           .setMessage("Please accept our permissions")
           .setPositiveButton("yes", (dialog, which) -> { result.askAgain(); })
           .setNegativeButton("no", (dialog, which) -> { dialog.dismiss(); })
           .show();
   */

   //the list of forever denied permissions, user has check 'never ask again'
   e.foreverDenied.forEach{
       
   }
   // you need to open setting manually if you really need it
   //e.goToSettings();
}
```

### Download 

[ ![Download](https://api.bintray.com/packages/florent37/maven/runtime-permission/images/download.svg) ](https://bintray.com/florent37/maven/runtime-permission/)
```groovy
implementation 'com.github.florent37:runtime-permission-kotlin:(last version)'
```

# RxJava

```java
new RxPermissions(this).request(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION))
    .subscribe(result -> {
        //all permissions already granted or just granted

        your action
    }, throwable -> {
        if (throwable instanceof RxPermissions.Error) {
            final PermissionResult result = ((RxPermissions.Error) throwable).getResult();

            //the list of denied permissions
            for (String permission : result.getDenied()) {
                
            }
            //permission denied, but you can ask again, eg:

            /*
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Please accept our permissions")
                    .setPositiveButton("yes", (dialog, which) -> { result.askAgain(); })
                    .setNegativeButton("no", (dialog, which) -> { dialog.dismiss(); })
                    .show();
            */

            //the list of forever denied permissions, user has check 'never ask again'
            for (String permission : result.getForeverDenied()) {
               
            }
            // you need to open setting manually if you really need it
            //result.goToSettings();
        }
    });
```

### Download 
```groovy
implementation 'com.github.florent37:runtime-permission-rx:(last version)'
```

# Java8

```java
askPermission(this)
     .request(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)

     .onAccepted((result) -> {
         //all permissions already granted or just granted

         your action
     })
     .onDenied((result) -> {
         //the list of denied permissions
         for (String permission : result.getDenied()) {
             appendText(resultView, permission);
         }
         //permission denied, but you can ask again, eg:

         /*
         new AlertDialog.Builder(MainActivity.this)
                 .setMessage("Please accept our permissions")
                 .setPositiveButton("yes", (dialog, which) -> { result.askAgain(); }) // ask again
                 .setNegativeButton("no", (dialog, which) -> { dialog.dismiss(); })
                 .show();
         */
     })
     .onForeverDenied((result) -> {
         //the list of forever denied permissions, user has check 'never ask again'
         for (String permission : result.getForeverDenied()) {
             
         }
         // you need to open setting manually if you really need it
         //result.goToSettings();
     })
     .ask();
```

### Download
 
[ ![Download](https://api.bintray.com/packages/florent37/maven/runtime-permission/images/download.svg) ](https://bintray.com/florent37/maven/runtime-permission/)
```groovy
implementation 'com.github.florent37:runtime-permission:(last version)'
```
 
# Java7

```java
askPermission(this, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
    .ask(new PermissionListener() {
        @Override
        public void onAccepted(RuntimePermission runtimePermission, List<String> accepted) {
            //all permissions already granted or just granted

            your action
        }

        @Override
        public void onDenied(RuntimePermission runtimePermission, List<String> denied, List<String> foreverDenied) {
            //the list of denied permissions
            for (String permission : denied) {
               
            }
            //permission denied, but you can ask again, eg:

                /*
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes", (dialog, which) -> { result.askAgain(); })
                        .setNegativeButton("no", (dialog, which) -> { dialog.dismiss(); })
                        .show();
                */

            //the list of forever denied permissions, user has check 'never ask again'
            for (String permission : foreverDenied) {
                
            }
            // you need to open setting manually if you really need it
            //runtimePermission.goToSettings();
        }
    });
```

# How to Contribute

We welcome your contributions to this project. 

The best way to submit a patch is to send us a [pull request](https://help.github.com/articles/about-pull-requests/). 

To report a specific problem or feature request, open a new issue on Github. 

# Credits

Manifest permission detection has been forked from https://github.com/sensorberg-dev/permission-bitte, 
thanks **Sensorberg GmbH**

Author: Florent Champigny [http://www.florentchampigny.com/](http://www.florentchampigny.com/)

Blog : [http://www.tutos-android-france.com/](http://www.tutos-android-france.com/)

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>

# License

    Copyright 2018 florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
