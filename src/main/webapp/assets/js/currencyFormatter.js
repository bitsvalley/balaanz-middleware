$(function () {
    "use strict";

    function formatCurrency(value) {
        // Add commas for thousands separator
        return value.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
    }

    $('.currency-input').each(function() {
        // Set Binded Field Value to Dummy Field
        $(this).val($(this).parent().find('.currency-original-field').val());

        // Track Change event of Dummy Field
        $(this).on('input', function() {
            // Remove non-digit characters
            let value = $(this).val().replace(/\D/g, '');
            // Set Dummy value to Binded Field
            $(this).parent().find('.currency-original-field').val(Number(value.split(',').join()));
            // Format the number with commas
            $(this).val(formatCurrency(value));
        });
    });
    
}(jQuery));