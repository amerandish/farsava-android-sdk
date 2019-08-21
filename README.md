
#  Farsava Android SDK
[![Documentation](https://img.shields.io/badge/api-reference-blue.svg)](https://bump.sh/doc/farsava)

## Overview
A repository for Android native software development kit (SDK) of Farsava API.

- Min SDK : 19
- Target SDK : 29

## Installation
Add it in your root build.gradle at the end of repositories:
```java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency
```java
dependencies {
		...
        	implementation 'com.github.amerandish:farsava-android-sdk:1.0.0-alpha'
        }
```
That's it!

## Usage
Build required object with a simple builder design pattern.

for endpoints:
```java
FarsAvaService apiService = new FarsAvaService.Builder()
		               .setJWTTokent(YOUR_TOKEN_HERE)
			       .build();
```
for live features: 
```java
FarsAvaLiveService liveService = new FarsAvaLiveService.Builder()
				    .setJWTTokent(YOUR_TOKEN_HERE)
			 	    .build();
```
for more check out the [sample application](https://github.com/amerandish/farsava-android-sdk/tree/master/app).

## License
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## Author
[Mohammad Hosein Kalantarian](http://www.mhksoft.com)

## Organization
[Amerandish](https://www.amerandish.com)
