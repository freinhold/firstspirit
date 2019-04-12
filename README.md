# FirstSpirit Extensions

## Information

In this module I add small snippets that are released for public.
You shouldn't use the whole module as it is, cause the build has a pretty old and not perfect setup and the functions don't have any relation to each other. Just extract the functions you need and add them to your own modules.

Of course you're welcome to contribute, create issues and so on.

## Contents

__Currently added functions:__
  
- SVG-Viewer  __(Deprecated)__ : Contains a StoreListener that adds a preview-image to SVG-files on creation/change and a toolbar-plugins that allows to show a preview of the svg in a swing-dialog (SiteArchitect only).
  - **Deprecation-comment**: You should change the StoreListener to an UploadHook or server-side StoreListener and provide a preview-function for ContentCreator instead of the Swing-Dialog.
      
- ProjectGroups-Hotspot: A hotspot for Option-based gom-elements that provides the groups of the current project

- GroupRestrictionValueService: A value-service that allows you to perform permission-based actions in forms, e. g. hide fields for specifiv groups. It's a simple ValueService - therefore you can easily extend it or use it as a template for building ValueServices.

## Setup

The module is build with maven. All dependencies are availabe in mvn central repository except for the FirstSpirit-libraries. You have to manually add them to your local repository or provide a repository in the pom.xml that contains the libraries.
Use _mvn clean install_ to build the fsm and _mvn eclipse:eclipse_ to create an eclipse-project.
