$( document ).ready(function() {

    var depError = "${depSumError}";

    console.log("depError: " + depError);

        $(".dep_line").hide();
        $(".cred_line").hide();



    $("select.acc_type").on("change", function () {
        var type = $(this).children("option:selected").val();

        if(type == "credit"){
            $(".cred_line").show();
            $(".dep_line").hide();
        }
        if(type == "deposit"){
            $(".dep_line").show();
            $(".cred_line").hide();
        }
        if(type == "standard"){
            $(".dep_line").hide();
            $(".cred_line").hide();
        }
    });
});

