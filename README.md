# chat-tutorial

Official Server source code for the Chat Tutorial using EzyFox ecosystem: https://www.youtube.com/watch?v=BI-CwpJjcPU&list=PLlZavoxtKE1IfKY7ohkLLyv6YkHMkvH6G

# Require

1. Java 8 or higher
2. Apache Maven 3.3+

# Description
1. chat-tutorial-app-api: contains app's request controller, app's event handler and which components just related to app
2. chat-tutorial-app-entry: contains `AppEntryLoader` class, you should not add classes here
2.1 chat-tutorial-app-entry/config/config.properties: app's configuration file
3. chat-tutorial-common: contains components that use by both app and plugin
4. chat-tutorial-plugin: contains plugin's event handler, plugin's request controller and which components just related to plugin. You will need handle `USER_LOGIN` event here
4.1 chat-tutorial-plugin/config/config.properties: plugin's configuration file
5. chat-tutorial-startup: contains `ApplicationStartup` class to run on local, you should not add classes here
5.1 chat-tutorial-startup/src/main/resources/log4j.properties: log4j configuration file

# Usage

On your IDE, you need:
1. Move to `chat-tutorial-startup` module 
2. Run `ApplicationStartup` in `src/main/java`
3. Run `ClientTest` in `src/test/java`

# License

Apache License, Version 2.0
