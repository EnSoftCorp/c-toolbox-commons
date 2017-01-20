---
layout: page
title: Tutorials
permalink: /tutorials/
---

If you haven’t already, [install](/c-toolbox-commons/install) the Toolbox Common plugin into Eclipse.

## C Toolbox Commons Shell Queries
To import the C Toolbox Commons common queries on the Atlas Shell you must first add the C Toolbox Commons plugin to the shell's dependencies and then import the `Queries` class.

- First if the shell is not already open then open the Atlas Shell by navigating to `Atlas` &gt; `Open Atlas Shell`.

- Open the Atlas Shell Settings (by clicking on the gear icon).

![Atlas Shell Settings](../images/tutorials/shell_settings.png)

- Search for the C Toolbox Commons plugin by typing "com.ensoftcorp.open.c.commons" in the search bar. Select the plugin and click the `Add` button.

![Search Plugins](../images/tutorials/search_plugins.png)

![Add Plugin](../images/tutorials/add_plugin.png)

- Select the Initialization Script tab in the Atlas Shell Settings window. At the bottom add a new line with the contents

        import com.ensoftcorp.open.c.commons.Queries._

and press the `OK` button.

![Import Queries Initialization](../images/tutorials/initialization.png)

- You can now run any of the methods in [Queries](https://ensoftcorp.github.io/c-toolbox-commons/javadoc/index.html) directly on the Atlas Shell.

## C Toolbox Commons Smart Views
To open the C Toolbox Commons Smart Views open the Atlas Smart Views window by navigating to `Atlas` &gt; `Open Atlas Smart View`. The C Toolbox Commons project contributes smart views which are accessible from the dropdown menu in the bottom left of the smart view window.

