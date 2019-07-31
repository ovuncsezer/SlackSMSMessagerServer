# SlackSMSMessagerServer
Server application implemented with Spring Boot to send messages between Slack and Firebase Notification Service. The application connects to Slack RTM API to recieve messages from a specific channel and sends it to a mobile device via push notification. The application also recieves messages sent via SMS through REST API and sends it to the Slack channel.

The Android code can be viewed from here: https://github.com/ovuncsezer/SlackSMSMessager

**For Installation**
1. The property values inside application.properties file should be filled accordingly.
2. The application server should be deployed on any appropriate application server.
3. URL of the server rest endpoint should be updated on mobile application code.
