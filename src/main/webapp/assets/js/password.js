$(function () {
    "use strict";

    function validatePassword(ele, value) {
        // Reset Dom State
        $(ele).parent().find('.password-field-status').children().removeClass('active');
        $(ele).parent().find('.password-field-status').removeClass('red');
        $(ele).parent().find('.password-field-status').removeClass('orange');
        $(ele).parent().find('.password-field-status').removeClass('green');

        // Check Password Strength
        let strength = 0;
        const expression = [/^.{8,20}$/, /[a-z]+/, /[0-9]+/, /[A-Z]+/,/[^a-zA-Z0-9]+/];
        jQuery.map(expression, function(regexp) {
        if(value.match(regexp))
            strength++;
            if (strength > 0) {
                $(ele).parent().find('.password-field-status').children().eq(strength - 1).addClass('active');
            }
        });

        // Set Color Classes
        if (strength <= 2 && strength > 0) {
            $(ele).parent().find('.password-field-status').addClass('red');
            $('.submit-frm-btn').attr('disabled', 'disabled');
        } else if (strength >= 3 && strength <= 4) {
            $(ele).parent().find('.password-field-status').addClass('orange');
            $('.submit-frm-btn').attr('disabled', 'disabled');
        } else if (strength > 4) {
            $(ele).parent().find('.password-field-status').addClass('green');
            $('.submit-frm-btn').removeAttr('disabled');
        } else {
            $('.submit-frm-btn').attr('disabled', 'disabled');
        }
    }

    $('.password-field').each(function() {
        // Password Rules Toggle
        $(this).on('focus', function() {
            $(this).parent().find('.password-field-rules').show();
        });
        $(this).on('blur', function() {
            $(this).parent().find('.password-field-rules').hide();
        });

        // Password Validation Based on Value Change
        $(this).on('input', function() {
            const passVal = $(this).val();
            validatePassword(this, passVal);
        });
    });
}(jQuery));
