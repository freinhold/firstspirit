------------------------------
# FirstSpirit Extensions
------------------------------


In this module I add small snippets that are released for public.
You shouldn't use the whole module as it is, cause the build has a pretty old and not perfect setup and the functions don't have any relation to each other. Just extrat the functions you need and add them to your own modules.


__Currently added functions:__
  
- SVG-Viewer  __(Deprecated)__ : Contains a StoreListener that adds a preview-image to SVG-files on creation/change and a toolbar-plugins that allows to show a preview of the svg in a swing-dialog (SiteArchitect only).
  - **Deprecation-comment**: You should change the StoreListener to a ploadHook or server-side StoreListener and provide a preview-function for ContentCreator instead of the Swing-Dialog.
      
- ProjectGroups-Hotspot: A hotspot for Option-based gom-elements that provides the groups of the current project
  

