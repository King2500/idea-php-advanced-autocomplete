<?php
namespace PHPSTORM_META {

    /**
     * Adds injection of project file reference in string literals for specified method or function.
     *
     * @param mixed       $functionReference Method or function reference, like: \FQN\Foo::bar()
     * @param int         $argumentIndex     Zero-based index of argument
     * @param string|null $relativeMode      Either '/' (= from top level), '.' (= from current dir) or '' (auto)
     * @return string
     */
    function xAdvancedInjectFileReference($functionReference, $argumentIndex, $relativeMode = null) {
        return "xAdvancedInjectFileReference " . $functionReference . " at " . $argumentIndex . ": " . $relativeMode;
    }

    /**
     * Adds injection of project folder reference in string literals for specified method or function.
     *
     * @param mixed       $functionReference Method or function reference, like: \FQN\Foo::bar()
     * @param int         $argumentIndex     Zero-based index of argument
     * @param string|null $relativeMode      Either '/' (= from top level), '.' (= from current dir) or '' (auto)
     * @return string
     */
    function xAdvancedInjectDirectoryReference($functionReference, $argumentIndex, $relativeMode = null) {
        return "xAdvancedInjectDirectoryReference " . $functionReference . " at " . $argumentIndex . ": " . $relativeMode;
    }

    registerArgumentsSet('x_advanced_fileref_relative_modes', '', '/', '.');
    expectedArguments(\PHPSTORM_META\xAdvancedInjectFileReference(), 2, argumentsSet('x_advanced_fileref_relative_modes'));
    expectedArguments(\PHPSTORM_META\xAdvancedInjectDirectoryReference(), 2, argumentsSet('x_advanced_fileref_relative_modes'));
}