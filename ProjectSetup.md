# Vaadin Setup #
Any version of Eclipse, but I used Java EE/Eclipse 3.7

Plugins:
  * SVN - Same as original setup (see below)
  * Google App Engine
  * Google Plugin
  * Google Web Toolkit SDK 2.4
  * Vaadin Plug-in for Eclipse (Vaadin 6.6.8)

Note: Deployed libs for Vaadin, GAE, and GWT are integrated into the project, so plugins are just there for IDE feature enhancement.

Note: Workaround for JDK 6.31u bug is to add -Dappengine.user.timezone.impl=UTC to run config. [Click](http://stackoverflow.com/questions/9414106/devserver-fails-after-updating-to-java-6u31) for more info.

# Original GWT Setup #
Galileo-based Eclipse (Spring's STS was originally used)

SVN Plugins:
  * Use Subclipse from the Marketplace. Or try,
  * http://subclipse.tigris.org/update_1.6.x
  * http://community.polarion.com/projects/subversive/download/eclipse/2.0/update-site/ (I used this with Linux)

Google Plugins:
http://dl.google.com/eclipse/plugin/3.5