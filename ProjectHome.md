Having sync problems? The latest version 0.17.4b can be downloaded at: [Moloko\_0.17.4b.apk](https://drive.google.com/file/d/0B-hF_H7DYd0NVENpX1ZsSEh5d1k/view?usp=sharing)
The changelog is available at the file info.


<img src='http://moloko.googlecode.com/svn/wiki/screenshots_0.17.0b/home_device.png' width='35%' />
<img src='http://moloko.googlecode.com/svn/wiki/screenshots_0.17.0b/home_device_land.png' width='60%' />

### Moloko ###
... is an Android client for Remember The Milk (RTM). The goal is to support nearly all features provided by the RTM web application. See WhatWorksSoFar. Moloko accesses all tasks via a local database. Network connection is only necessary for background synchronization. Currently it supports German, English and French language.

### Testing ###
Please activate in app error reporting and send log files if you encounter strange behavior. Also feel free to enter issues in the [Issue tracker](https://code.google.com/p/moloko/issues/list).


### Platform ###
The target SDK is Android 2.2. Moloko uses features of Android 3.0+ by compatibility libraries.


### RTM access rights ###
Moloko can operate in 2 of the three possible RTM access modes (see [RTMAccessLevels](http://code.google.com/p/moloko/wiki/RTMAccessLevels)).

  * _Read_: This allows Moloko to synchronize only incoming changes from the RTM server. The UI will not allow any changes to any kind of elements.

  * _Read + Write + Delete_: This allows Moloko to synchronize incoming and outgoing changes. The UI will allow changes to to any kind of elements implemented so far.

> Note: The access level _Read + Write_, which prohibits the deletion of elements, can not be supported since Moloko uses transactional synchronization to provide best data integrity for both sides. This means if any synchronization step fails, local or remote, all changes done so far will be reverted to the state before the synchronization. The RTM API has support for transactions but reverting a change on the RTM server requires _delete_ permissions.

### Screens ###
See [Screenshots](http://code.google.com/p/moloko/wiki/Screenshots)