const mealAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );

    var startDate = $('#startDate');
    var endDate = $('#endDate');
    var startTime = $('#startTime');
    var endTime = $('#endTime');
    var dateTime = $('#dateTime');

    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });
    endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });
    startTime.datetimepicker({
        datepicker: false,
        format: 'H:i'
    });
    endTime.datetimepicker({
        datepicker: false,
        format: 'H:i'
    });
    dateTime.datetimepicker({
        format: 'Y-m-d H:i'
    });
});

function filter() {
    const formData = $('#filterForm').serialize();
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: formData
    }).done(function (data) {
        updateTableByData(data);
        successNoty("Meals filtered");
    }).fail(function (jqXHR) {
        failNoty(jqXHR);
    });
}

function resetFilter() {
    $('#filterForm')[0].reset();
    $.ajax({
        type: "GET",
        url: mealAjaxUrl
    }).done(function (data) {
        updateTableByData(data);
        successNoty("Filter reset");
    }).fail(function (jqXHR) {
        failNoty(jqXHR);
    });
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}
