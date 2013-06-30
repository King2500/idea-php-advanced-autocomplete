# PhpStorm IDE Plugin:<br>PHP Advanced AutoComplete#

Plugin URL: http://plugins.jetbrains.com/plugin/7276?pr=phpStorm

Adds auto-completion support for various built-in PHP functions, where parameter is a string literal.


The following functions are currently supported:

* <b>header / header_remove</b><br>
    HTTP response headers, status codes, charsets, mime-types, and much more

* <b>date</b><br>
    Format characters and common format strings

* <b>htmlentities / htmlspecialchars</b><br>
    Supported charsets

* <b>mb_string functions</b><br>
    Charset, where required; types for mb_get_info and supported languages for mb_language

* <b>ini_get / ini_set / ini_restore / get_cfg_var</b><br>
    Known INI variable names

* <b>extension_loaded</b><br>
    Known PHP extensions

* <b>fopen / popen</b><br>
    File modes

* <b>mysql_connect / mysqli_connect</b><br>
    Currently only &quot;localhost&quot; for host parameter<br><br></li>

<b>Important:</b><br>
You have to start a string literal and press <i>Ctrl</i> + <i>Space</i> to activate the completion popup

If you have further suggestions/ideas, just send me an e-mail to <i>phpstorm at king2500.net</i>.
