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

- You can now run any of the methods in the [Queries](https://ensoftcorp.github.io/c-toolbox-commons/javadoc/index.html) class. Example usage of the queries can be found below. 

### Query Usage

<table>
  <thead>
    <tr>
      <th style="text-align: left"><strong>Function</strong></th>
      <th style="text-align: left"><em>functions</em></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align: left"><strong>Parameters</strong></td>
      <td style="text-align: left">Parameters (functionNames): A list of function names as Strings</td>
    </tr>
    <tr>
      <td style="text-align: left"><strong>Description</strong></td>
      <td style="text-align: left">Returns the set of functions where their names matches the any of the names given the <em>functionNames</em> list. A (*) in <em>functionNames</em> represents a wildcard that matches any string.</td>
    </tr>
    <tr>
      <td style="text-align: left"><strong>Example&nbsp;(1)</strong></td>
      <td style="text-align: left"><p>Return the functions named "dswrite" and "dsread"</p> <p><em>var funcs = functions("dswrite", "dsread")</em></p> <p><em>show(funcs)</em></p></td>
    </tr>
    <tr>
      <td style="text-align: left"><strong>Example&nbsp;(2)</strong></td>
      <td style="text-align: left"><p>Return all functions where their names start with/match "ds*" or "dg*"</p> <p><em>var funcs = functions("ds*", "dg*")</em></p> <p><em>show(funcs)</em></p></td>
    </tr>
  </tbody>
</table>

<br />

<table>
  <thead>
    <tr>
      <th style="text-align: left"><strong>Function</strong></th>
      <th style="text-align: left">globals</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align: left"><strong>Parameters</strong></td>
      <td style="text-align: left">Parameter (names): A list of global variable names as Strings</td>
    </tr>
    <tr>
      <td style="text-align: left"><strong>Description</strong></td>
      <td style="text-align: left">Returns the nodes representing the global variables given by the parameter <em>name</em>. A * in <em>name</em> represents a wildcard that matches any string.</td>
    </tr>
    <tr>
      <td style="text-align: left"><strong>Example&nbsp;(1)</strong></td>
      <td style="text-align: left"><p>Return the global variable named "devtab"</p> <p><em>var globalVar = globals("devtab")</em></p><p><em>show(globalVar)</em></p></td>
    </tr>
    <tr>
      <td style="text-align: left"><strong>Example&nbsp;(2)</strong></td>
      <td style="text-align: left"><p>Return all global variables where their names start with/match "dv*"</p> <p><em>var globalVars = globals("dv*")</em></p> <p><em>show(globalVars)</em></p></td>
    </tr>
  </tbody>
</table>

<br />

<table>
  <thead>
    <tr>
      <th style="text-align: left"><strong>Function</strong></th>
      <th style="text-align: left">ref</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align: left"><strong>Parameters</strong></td>
      <td style="text-align: left">Parameter (object): the set of global variables and/or types</td>
    </tr>
    <tr>
      <td style="text-align: left"><strong>Description</strong></td>
      <td style="text-align: left">Returns the set of functions referencing (read/write) the given global variables and/or types (structures) given in parameter (object).</td>
    </tr>
    <tr>
      <td style="text-align: left"><strong>Example</strong></td>
      <td style="text-align: left"><p>Return all functions referencing the structures/types "dreq", "epacket"</p> <p><em>var refFuncs = ref(ts)</em></p>
<p><em>show(refFuncs)</em></p></td>
    </tr>
  </tbody>
</table>

## C Toolbox Commons Smart Views
To open the C Toolbox Commons Smart Views open the Atlas Smart Views window by navigating to `Atlas` &gt; `Open Atlas Smart View`. The C Toolbox Commons project contributes smart views which are accessible from the dropdown menu in the bottom left of the smart view window.

