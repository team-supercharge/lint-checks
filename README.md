# lint-checks

`lint-checks` is a project that contains custom Android Lint checks for frequently occurred mistakes we usually notice during our team's merge reviews. By applying these lint checks you don't need to care about the mistakes they can prevent.

It contains two set of Android Lint checks:
 - `Common checks` contains mainly formatting specific rules for Java and Kotlin languages.
 - `Android checks` contains Android specific rules.

# Integration

Get the latest artifact via gradle
```groovy
implementation 'io.supercharge:lint-checks-common-rules:0.3.0'
```
```groovy
implementation 'io.supercharge:lint-checks-android-rules:0.3.0'
```

# Inspiration

- [vanniktech/lint-rules](https://github.com/vanniktech/lint-rules)
- [googlesamples/android-custom-lint-rules](https://github.com/googlesamples/android-custom-lint-rules)
- [Android Lint default rule set](https://android.googlesource.com/platform/tools/base/+/studio-master-dev/lint/libs/lint-checks)

# License

`lint-checks` is opensource, contribution and feedback are welcome!

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


```
Copyright 2019 Supercharge

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
# Author

- [BalazsR](https://github.com/balazsR)
- [Supercharge](https://github.com/team-supercharge)
