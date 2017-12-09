/* 
 * Copyright (C) 2017- I MUSIC - All Rights Reserved
 * 
 * Version 1.0
 * Author: Mike Kreager
 * 
 */


/*
 * This file contains a function for populating values to the shipping
 * address section of the account page.
 */

// Copy billing information to shipping information
$(document).ready(function() {
    $("#sameAsBilling").on("change", function(){
        if (this.checked) {
	    $("[id='shippingFullName']").val($("[id='billingFullName']").val());
	    $("[id='shippingAddressLine1']").val($("[id='billingAddressLine1']").val());
	    $("[id='shippingAddressLine2']").val($("[id='billingAddressLine2']").val());
	    $("[id='shippingCity']").val($("[id='billingCity']").val());
	    $("[id='shippingProvince']").val($("[id='billingProvince']").val());
	    $("[id='shippingCountry']").val($("[id='billingCountry']").val());
	    $("[id='shippingZip']").val($("[id='billingZip']").val());
	    $("[id='shippingPhone']").val($("[id='billingPhone']").val());
        }
    });
});
