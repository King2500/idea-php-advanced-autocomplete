<?php
namespace PHPSTORM_META {
    function xAdvancedInjectFileReference($functionReference, $argumentIndex, $relativeMode = null) {
        return "xAdvancedInjectFileReference " . $functionReference . " at " . $argumentIndex . ": " . $relativeMode;
    }

    registerArgumentsSet('x_advanced_fileref_relative_modes', '', '/', '.');
    expectedArguments(\PHPSTORM_META\xAdvancedInjectFileReference(), 2, argumentsSet('x_advanced_fileref_relative_modes'));
}