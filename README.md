Event Notification service
=======================

A.Requirement
-------------

1. Applications can generate event notifications to its users based on the business requirements. Ex: New SkU created, New Order received, Inventory updated, Parking space error found, Overtime found for an employee in Attendance management system etc.
2. When user logs in, he/she should be able to see event notifications generated in the last 7 days. This should be configurable in the back end, so that the duration can be adjusted for applications based on business needs.
3. During the logged in period, if any notification is generated, user should be able to view them in real time.
4. User should be able to delete a notification
5. By default, all notifications will be sent. However, user should be able to disable/enable a particular notification type, if he/she finds it not relavent.

B. Architecture & Design approach
---------------------------------

This will be a new Spring boot application and will be listening to a message queue (NOTIFICATION-SVC-<env>) for the new notification messages from event workflow service. It will expose a set of REST APIs to manage the notifications. It is explained with the following use cases.


Use case-1) Notification Generation
-------------------------------

1. The work flow service will send event notification payload to notification-svc via message queue. 
2. notification-svc will receive the message from queue and store it in db

Sample payload:

```{
	"notificationType": "CREATE_ORDER",
	"companyId": "7",
	"templateVariables": " {\"orderId\":\"4355\",\"orderDate\":\"12/12/2017 \"}",		"
	
}```

Use case-2) Sending Notifications to Users after successful login
---------------------------------------------------

1. When user logs in, UI will make a REST call to notification-svc (via gateway) to get notifications generated in the last nn days. The response should be grouped by notification type. UI will display the grouped messages based on notificationType. This is something similar to notification display on Mac
2. User can delete a notification message, notification-svc will provide a API to mark the message as deleted in the db for that user. User cannot see that message again
3. The messages read by user can be marked as read. UI will make a REST call to notification-svc


The service should generate the actual message based on placeholder tags (or template variables)

```{
	"notificationType": "CREATE_ORDER",
	"companyId": "7",
	"templateVariables": " {\"orderId\":\"4355\",\"orderDate\":\"12/12/2017 \"}",		"
	
}```

The above notification will get translated to below message based on template as below:

template: "A new customer Order # {orderId} with order date {orderDate} created."

message: "A new customer Order #4355 with order date 12/12/2017 created."

REST APIs:

GET /notifications/{userId} -> to get all notificationTypes along with user unsubscribed info




Use case-3) Sending Notifications in real time to logged in users
---------------------------------------------------

User should able to see notifications in real time during login period. We will use web socket to achieve that functionality. Whenever notification-svc received a new notification payload, it will send to all connections from UI. Node server will be listening to socket connections from browser page. Node server listening to connection: The notification-svc will push every new notifications received to a message queue. The the node server will pick the message and send to all connected pages.

Note: UI needs to make a call to /GET call all unsubscribed notificationTypes for a user. This will help the UI logic to keep or reject a real-time message received via socket.

REST API:

GET /unsubscribedNotificationTypes/{userId}

Use case-4) Ability Filter Notifications
-----------------------------------------

By default user will see all notifications. UI app can provide a screen to unsubscribe/subscribe any notification types for the logged in user. The page will show all possible notification types and user can opt-in/out using toggle button.

REST APIs:

GET /notificationTypes/{userId} -> to get all notificationTypes along with user unsubscribed info
PATCH /notificationTypes/{userId} > to unsubscribe/subscribe


Use case-5) Ability to create notification templates
---------------------------------------------------

The admin user will be able to create new message template based on the available template variables in the system. The UI app should show all available tags and user can view/create/edit/delete templates

The following REST APIs can be used to handle templates:

GET /templates -> to get all templates
GET /templateTags -> to get all template tags
POST /templates -> to create a new template
PUT /templates/{id} -> to update a template
DELETE /templates/{id} -> to delete a template
PATCH /templates/{id} -> to activate or deactivate a template

C. eventNotification schema
--------------------------

# NotificationType -> store notification types (String type, String title, String description, boolean active)
# Notification -> All notification messages (String notificationData, Instant notificationOn)
# UserProcessedNotification -> Store user read/delete  (String notificationId, String userId, boolean read, boolean deleted, Instant processedOn)
# UserUnsubscribedNotificationType -> User Unsubscribed notification types   (String notificationTypeId, String userId, Instant unsubscribedOn)
# NotificationTemplate -> Store notification templates (String name, String bodyTemplate, String description, boolean active, String notificationTypeId).
# TemplateTag -> All tags needed for creating templates (String tag, String description, boolean active)
# NotificationSetting -> holds data to processed notifications (String name, String description, String value, boolean active).

 NotificationSetting collection is used for any notification setting. Ex: It will hold data to return last 7 days of notification

We should provide mongobee Changelog to add data into NotificationType, TemplateTag and NotificationSetting collections

Some notifications may have a button navigate to a specific page. Ex: New Order creation notification should direct the user to Order page. The template designer can use html editor to create needed content, so that it will look appealing on browser page.

Example Notification message that may be sent to UI:

[{
	"title": "New Orders",
	"messages": [{
		"date": "12/31/2017 04:45 PM",
		"body": "A new customer Order #342 with order date 12/31/2017 created. Click here to avail 5% discount ....",
		"read": false
	}, {
		"date": "12/12/2017 11:15 AM",
		"body": "A new customer Order #4355 with order date 12/12/2017 created.",
		"read": true
	}]
}, {
	"title": "Inventory Update",
	"messages": [{
		"date": "12/29/2017 04:45 PM",
		"body": "New product abc meat arrived. Quantity: 500",
		"read": false
	}, {
		"date": "12/12/2017 10:34 AM",
		"body": "Threshold quantity for xyz is gone below minimum level 3",
		"read": true
	}]
}]
