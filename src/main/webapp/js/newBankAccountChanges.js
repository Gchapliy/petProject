$( document ).ready(function() {

    if ($(".dep_e")[0]){
        $(".cred_line").hide();
        $("select.acc_type").val('deposit')
    } else if($(".cred_e")[0]){
        $(".dep_line").hide();
        $("select.acc_type").val('credit')
    } else {
        $(".cred_line").hide();
        $(".dep_line").hide();
    }

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

    $("select.depositTerm").on("change", function () {
        var months = $(this).children("option:selected").val();

        if(months == 3){
            $("input.dep_perc").val('10');
        }else if(months == 6){
            $("input.dep_perc").val('15');
        }else if(months == 12){
            $("input.dep_perc").val('20');
        }
    });

    $("select.creditTerm").on("change", function () {
        var months = $(this).children("option:selected").val();

        if(months == 6){
            $("input.cred_perc").val('45');
        }else if(months == 12){
            $("input.cred_perc").val('50');
        }else if(months == 18){
            $("input.cred_perc").val('55');
        }else if(months == 24){
            $("input.cred_perc").val('60');
        }
    })
});

