/**
 * @author Zhang Deng
 * Created on 2017/6/11.
 */

var areas = [
    '北京', '贵州', '河南', '青海', '内蒙古', '新疆', '西藏'
];

var $searchConditionsContainer = $('#searchConditionsContainer');

$(function () {
    acceptIntegerOnly($searchConditionsContainer.find('input[name=grade]'));
    acceptIntegerOnly($searchConditionsContainer.find('input[name=ranking]'));
    initCategorySelect();
    initBatchSelect();
    initAreaSelect();
    initSchoolSelect();

    $searchConditionsContainer.find('button[name=searchBtn]').click(function () {
        if (validateForm()) {
            $.ajax({
                url: 'lnfsdxq/test',
                type: 'POST',
                cache: false,
                dataType: 'json',
                async: false,
                data: extractForm(),
                success: function (result) {
                    console.info(result);
                    if (result && result.probabilityCalaDTOs && result.probabilityCalaDTOs.length > 0) {
                        var resultTableHtml = '<table class="table table-striped">';
                        resultTableHtml += '<thead>';
                        resultTableHtml += '<tr>';
                        resultTableHtml += '<th>序号</th>';
                        resultTableHtml += '<th>院校代码</th>';
                        resultTableHtml += '<th>院校名称</th>';
                        resultTableHtml += '<th>省/市</th>';
                        resultTableHtml += '<th>批次</th>';
                        resultTableHtml += '<th>高校类型</th>';
                        resultTableHtml += '<th>招生计划</th>';
                        resultTableHtml += '<th>全国排名</th>';
                        resultTableHtml += '<th>录取概率</th>';
                        resultTableHtml += '</tr>';
                        resultTableHtml += '</thead>';
                        resultTableHtml += '<tbody>';
                        $.each(result.probabilityCalaDTOs, function (index) {
                            resultTableHtml += '<tr>';
                            resultTableHtml += '<td>' + (index + 1) + '</td>';
                            resultTableHtml += '<td>' + this.collegeCode + '</td>';
                            resultTableHtml += '<td>' + this.collegeName + '</td>';
                            resultTableHtml += '<td>' + this.areaName + '</td>';
                            resultTableHtml += '<td>' + this.batchName + '</td>';
                            resultTableHtml += '<td>' + this.collegeType + '</td>';
                            resultTableHtml += '<td>' + this.enrollCount + '</td>';
                            resultTableHtml += '<td>' + this.collegeRanking + '</td>';
                            resultTableHtml += '<td>' + this.gaiLv + '</td>';
                            resultTableHtml += '</tr>';
                        });
                        resultTableHtml += '</tbody>';
                        resultTableHtml += '</table>';
                        $('div#resultTableContainer').html(resultTableHtml);
                    } else {
                        $('div#resultTableContainer').html('<span>无预测结果</span>');
                    }
                }
            });
        }
    });

    $searchConditionsContainer.find('button[name=resetSearchConditionBtn]').click(function () {
        resetSearchConditions();
    });
});

/**
 * 重置搜索条件
 */
function resetSearchConditions() {
    $searchConditionsContainer.find('input').val('');
    $searchConditionsContainer.find('select').val('').trigger('change');
}

function initCategorySelect() {
    $searchConditionsContainer.find('select[name=category]').select2({
        placeholder: '请选择科别',
        allowClear: true
    });
    $searchConditionsContainer.find('select[name=category]').val('').trigger('change');
}

function initBatchSelect() {
    $searchConditionsContainer.find('select[name=batch]').select2({
        placeholder: '请选择批次',
        allowClear: true
    });
    $searchConditionsContainer.find('select[name=batch]').val('').trigger('change');
}

function initAreaSelect() {
    var areaOptionsHtml = '';
    $.each(areas, function () {
        areaOptionsHtml += '<option value="' + this + '">' + this + '</option>';
    });
    $searchConditionsContainer.find('select[name=areaName]').select2({
        placeholder: '请选择省/市',
        allowClear: true
    });
    $searchConditionsContainer.find('select[name=areaName]').change(function () {
        initSchoolSelect();
    });
    $searchConditionsContainer.find('select[name=areaName]').html(areaOptionsHtml);
    $searchConditionsContainer.find('select[name=areaName]').val('').trigger('change');
}

function initSchoolSelect() {
    $searchConditionsContainer.find('select[name=targetSchool]').select2({
        placeholder: '请选择院校',
        allowClear: true
    });
    $.ajax({
        url: 'college/findByArea',
        type: 'POST',
        cache: false,
        dataType: 'json',
        async: false,
        data: {area: $searchConditionsContainer.find('select[name=areaName]').val()},
        success: function (result) {
            if (result) {
                var areaOptionsHtml = '';
                $.each(result, function () {
                    areaOptionsHtml += '<option value="' + this.name + '">' + this.name + '</option>';
                });
                $searchConditionsContainer.find('select[name=targetSchool]').html(areaOptionsHtml);
                $searchConditionsContainer.find('select[name=targetSchool]').val('').trigger('change');
            }
        }
    });
}

function validateForm() {
    if (!$searchConditionsContainer.find('input[name=name]').val()) {
        bootbox.alert("请输入您的姓名");
        return false;
    }

    if (!$searchConditionsContainer.find('input[name=grade]').val()) {
        bootbox.alert("请输入您的考试分数");
        return false;
    }

    if (!$searchConditionsContainer.find('input[name=ranking]').val()) {
        bootbox.alert("请输入您的分数名次");
        return false;
    }

    if (!$searchConditionsContainer.find('select[name=category]').val()) {
        bootbox.alert("请选择您的科别");
        return false;
    }

    if (!$searchConditionsContainer.find('select[name=batch]').val()) {
        bootbox.alert("请选择您的意向批次");
        return false;
    }

    return true;
}

function extractForm() {
    var requestForm = {};
    requestForm['name'] = $searchConditionsContainer.find('input[name=name]').val();
    requestForm['grade'] = $searchConditionsContainer.find('input[name=grade]').val();
    requestForm['ranking'] = $searchConditionsContainer.find('input[name=ranking]').val();
    requestForm['category'] = $searchConditionsContainer.find('select[name=category]').val();
    requestForm['batch'] = $searchConditionsContainer.find('select[name=batch]').val();
    requestForm['areaName'] = $searchConditionsContainer.find('select[name=areaName]').val();
    requestForm['targetSchool'] = $searchConditionsContainer.find('select[name=targetSchool]').val();
    requestForm['targetMajor'] = $searchConditionsContainer.find('input[name=targetMajor]').val();
    return requestForm;
}

/**
 * 使传入的input对象只接受整数的输入
 *
 * @param input input对象
 */
function acceptIntegerOnly(input) {
    $(input).bind('paste', function () {
        return false;
    });
    $(input).keydown(function (e) {
        e = e ? e : window.event;
        var keyCode = e.which ? e.which : e.keyCode;
        // 主键盘0~9数字键键值: 48~57
        // 小键盘0~9数字键键值: 96-105
        // 上下左右方向键键值: 37~40
        // Backspace键键值为8
        // Delete键键值为46
        return ((keyCode >= 48 && keyCode <= 57)
        || (keyCode >= 96 && keyCode <= 105)
        || (keyCode >= 37 && keyCode <= 40)
        || (keyCode == 8)
        || (keyCode == 46));
    });
}
