Runtime Permission
===================

**Work in progress**

Simpliest way to ask runtime permissions on Android, choose your way : 
- [Kotlin](https://github.com/florent37/RuntimePermission#kotlin)
- [Kotlin with Coroutines](https://github.com/florent37/RuntimePermission#kotlin-coroutines)
- [RxJava](https://github.com/florent37/RuntimePermission#rxjava)
- [Java8](https://github.com/florent37/RuntimePermission#java8)
- [Java7](https://github.com/florent37/RuntimePermission#java7)

**No need to override Activity or Fragment **`onPermissionResult(code, permissions, result)`**using this library, you just have to executue RuntimePermission's methods** 
This will not cut your code flow

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
```groovy
implementation 'com.github.florent37:runtimepermission-kotlin:(last version)'
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
```groovy
implementation 'com.github.florent37:runtimepermission-kotlin:(last version)'
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
implementation 'com.github.florent37:runtimepermission-rx:(last version)'
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
```groovy
implementation 'com.github.florent37:runtimepermission:(last version)'
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

### Download 
```groovy
implementation 'com.github.florent37:runtimepermission:(last version)'
```

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
