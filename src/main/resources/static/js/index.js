/**
 * @author Zhang Deng
 * Created on 2017/6/11.
 */

$.fn.select2.defaults.set("theme", "bootstrap");

var areas = [
    '北京', '广东', '山东', '江苏', '河南', '上海', '河北', '浙江', '陕西', '湖南', '重庆', '福建', '天津', '云南', '四川', '广西', '安徽', '海南', '江西', '湖北', '山西', '辽宁', '台湾', '黑龙江', '内蒙古', '香港', '澳门', '贵州', '甘肃', '青海', '新疆', '西藏', '吉林', '宁夏'
];

// 院校各专业当年好生计划
var collegeDnzsjhMap = {};

var $searchConditionsContainer = $('#searchConditionsContainer');

$(function () {
    acceptIntegerOnly($searchConditionsContainer.find('input[name=grade]'));
    acceptIntegerOnly($searchConditionsContainer.find('input[name=ranking]'));
    initAreaSelect();
    initSchoolSelect();

    $searchConditionsContainer.find('button[name=searchBtn]').click(function () {
        if (validateForm()) {
            $('#waitingDialog').modal("show");
            $.ajax({
                url: 'lnfsdxq/getTargetColleges',
                type: 'POST',
                cache: false,
                dataType: 'json',
                async: false,
                data: extractForm(),
                success: function (result) {
                    if (result && result.probabilityCalaDTOs && result.probabilityCalaDTOs.length > 0) {
                        collegeDnzsjhMap = {};
                        var years = [];
                        for (var year in result.probabilityCalaDTOs[0].yxRankingMap) {
                            years.push(year);
                        }
                        var resultTableHtml = '<table class="table table-bordered table-condensed table-hover">';
                        resultTableHtml += '<caption style="text-align: center;font-size: 16px;font-weight: bold">目标院校历年录取数据分析及2017录取概率预测报告</caption>';
                        resultTableHtml += '<thead>';
                        resultTableHtml += '<tr>';
                        resultTableHtml += '<th rowspan="2">序号</th>';
                        resultTableHtml += '<th rowspan="2">院校代码</th>';
                        resultTableHtml += '<th rowspan="2">院校名称</th>';
                        resultTableHtml += '<th rowspan="2">省市</th>';
                        resultTableHtml += '<th rowspan="2">批次</th>';
                        resultTableHtml += '<th rowspan="2">院校类别</th>';
                        resultTableHtml += '<th rowspan="2">全国排名</th>';
                        resultTableHtml += '<th colspan="' + years.length + '">招生计划</th>';
                        resultTableHtml += '<th colspan="' + years.length + '">线差数据</th>';
                        resultTableHtml += '<th rowspan="2">线差法概率</th>';
                        resultTableHtml += '<th colspan="' + years.length + '">名次数据</th>';
                        resultTableHtml += '<th rowspan="2">位次法概率</th>';
                        resultTableHtml += '<th colspan="' + years.length + '">志愿号数据</th>';
                        resultTableHtml += '<th rowspan="2">操作</th>';
                        resultTableHtml += '</tr>';
                        resultTableHtml += '<tr>';
                        for (var i = 0; i < years.length; i++) {
                            resultTableHtml += '<th>' + years[i] + '</th>';
                        }
                        for (i = 0; i < years.length; i++) {
                            resultTableHtml += '<th>' + years[i] + '</th>';
                        }
                        for (i = 0; i < years.length; i++) {
                            resultTableHtml += '<th>' + years[i] + '</th>';
                        }
                        for (i = 0; i < years.length; i++) {
                            resultTableHtml += '<th>' + years[i] + '</th>';
                        }
                        resultTableHtml += '</tr>';
                        resultTableHtml += '</thead>';
                        resultTableHtml += '<tbody>';
                        $.each(result.probabilityCalaDTOs, function (index) {
                            collegeDnzsjhMap[this.collegeName] = this.dnzsjhMap;
                            resultTableHtml += '<tr>';
                            resultTableHtml += '<td rowspan="3">' + (index + 1) + '</td>';
                            resultTableHtml += '<td rowspan="3">' + this.collegeCode + '</td>';
                            resultTableHtml += '<td rowspan="3"><a style="cursor: pointer" onclick="showDnzsjh(this, \'' + this.collegeName + '\')">' + this.collegeName + '</a></td>';
                            resultTableHtml += '<td rowspan="3">' + (this.areaName == null ? '' : this.areaName) + '</td>';
                            resultTableHtml += '<td rowspan="3">' + this.batchName + '</td>';
                            resultTableHtml += '<td rowspan="3">' + (this.collegeType == null ? '其它' : this.collegeType) + '</td>';
                            resultTableHtml += '<td rowspan="3">' + (this.collegeRanking == null ? '' : this.collegeRanking) + '</td>';
                            // 第一行
                            for (var year in this.yxRankingMap) {
                                resultTableHtml += '<td rowspan="3">' + this.yxRankingMap[year].enrollCunt + '</td>';
                            }
                            for (var year in this.yxRankingMap) {
                                resultTableHtml += '<td>' + Math.round(this.yxRankingMap[year].highGrade - this.yxRankingMap[year].standardGrade).toFixed(1) + '</td>';
                            }
                            resultTableHtml += '<td rowspan="3">' + (Math.round(this.xcfGaiLv * 10000) / 100).toFixed(2) + '%' + '</td>';
                            for (var year in this.yxRankingMap) {
                                resultTableHtml += '<td>' + this.yxRankingMap[year].highRanking + '</td>';
                            }
                            resultTableHtml += '<td rowspan="3">' + (Math.round(this.gaiLv * 10000) / 100).toFixed(2) + '%' + '</td>';
                            for (var year in this.volunteerInfoMap) {
                                var volunteerInfo = '';
                                if (this.volunteerInfoMap[year]) {
                                    var volunteerInfoItems = this.volunteerInfoMap[year].split('--');
                                    for (var j = 0; j < volunteerInfoItems.length; j++) {
                                        volunteerInfo += volunteerInfoItems[j] + '--<br>'
                                    }
                                    volunteerInfo = volunteerInfo.substring(0, volunteerInfo.length - 6);
                                }
                                resultTableHtml += '<td rowspan="3">' + volunteerInfo + '</td>';
                            }
                            resultTableHtml += '<td rowspan="3"><a class="btn btn-primary btn-xs" onclick="showMajorDetails(\'' + this.collegeName + '\', this)">查看专业详情</a></td>';
                            resultTableHtml += '</tr>';
                            // 第二行
                            resultTableHtml += '<tr>';
                            for (var i = 0; i < years.length; i++) {
                                resultTableHtml += '<td>' + Math.round(this.yxRankingMap[years[i]].avgGrade - this.yxRankingMap[years[i]].standardGrade).toFixed(1) + '</td>';
                            }
                            for (i = 0; i < years.length; i++) {
                                resultTableHtml += '<td>' + Math.ceil(this.yxRankingMap[years[i]].avgRanking) + '</td>';
                            }
                            resultTableHtml += '</tr>';
                            // 第三行
                            resultTableHtml += '<tr>';
                            for (i = 0; i < years.length; i++) {
                                resultTableHtml += '<td>' + Math.round(this.yxRankingMap[years[i]].lowGrade - this.yxRankingMap[years[i]].standardGrade).toFixed(1) + '</td>';
                            }
                            for (i = 0; i < years.length; i++) {
                                resultTableHtml += '<td>' + this.yxRankingMap[years[i]].lowRanking + '</td>';
                            }
                            resultTableHtml += '</tr>';
                        });
                        resultTableHtml += '</tbody>';
                        resultTableHtml += '</table>';
                        $('div#resultTableContainer').html(resultTableHtml);

                        showTableHead();
                    } else {
                        $('div#resultTableContainer').html('<span>无预测结果</span>');
                    }
                    $('#waitingDialog').modal("hide");
                }
            });
        }
    });

    $searchConditionsContainer.find('button[name=resetSearchConditionBtn]').click(function () {
        resetSearchConditions();
    });
});

function showMajorDetails(collegeName, obj) {
    if ($(obj).text() == '查看专业详情') {
        $('#waitingDialog').modal("show");
        var requestForm = extractForm();
        requestForm['targetSchool'] = collegeName;
        $.ajax({
            url: 'lnfsdxq/getTargetCollegeWithMajor',
            type: 'POST',
            cache: false,
            dataType: 'json',
            async: false,
            data: requestForm,
            success: function (result) {
                if (result && result.probabilityCalaDTOs && result.probabilityCalaDTOs.length > 0) {
                    var probabilityCalaDTO = result.probabilityCalaDTOs[0];
                    var majorEnrollDTOMap = probabilityCalaDTO.majorEnrollDTOMap;
                    var resultTableHtml = '';
                    for (var major in  majorEnrollDTOMap) {
                        resultTableHtml += '<tr class="major-item-' + collegeName + '">';
                        resultTableHtml += '<td rowspan="3" colspan="4"></td>';
                        resultTableHtml += '<td rowspan="3">' + probabilityCalaDTO.batchName + '</td>';
                        resultTableHtml += '<td rowspan="3">' + (probabilityCalaDTO.collegeType == null ? '其它' : probabilityCalaDTO.collegeType) + '</td>';
                        resultTableHtml += '<td rowspan="3">' + major + '</td>';

                        var lnzymcMap = majorEnrollDTOMap[major].lnzymcMap;
                        // 第一行
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td rowspan="3">' + lnzymcMap[year].enrollCunt + '</td>';
                        }
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + Math.round(lnzymcMap[year].highGrade - lnzymcMap[year].standardGrade).toFixed(1) + '</td>';
                        }
                        resultTableHtml += '<td rowspan="3">' + (Math.round(majorEnrollDTOMap[major].xcfGaiLv * 10000) / 100).toFixed(2) + '%' + '</td>';
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + lnzymcMap[year].highRanking + '</td>';
                        }
                        resultTableHtml += '<td rowspan="3">' + (Math.round(majorEnrollDTOMap[major].gaiLv * 10000) / 100).toFixed(2) + '%' + '</td>';
                        for (var year in majorEnrollDTOMap[major].volunteerInfoMap) {
                            var volunteerInfo = '';
                            if (majorEnrollDTOMap[major].volunteerInfoMap[year]) {
                                var volunteerInfoItems = majorEnrollDTOMap[major].volunteerInfoMap[year].split('--');
                                for (var j = 0; j < volunteerInfoItems.length; j++) {
                                    volunteerInfo += volunteerInfoItems[j] + '--<br>'
                                }
                                volunteerInfo = volunteerInfo.substring(0, volunteerInfo.length - 6);
                            }
                            resultTableHtml += '<td rowspan="3">' + volunteerInfo + '</td>';
                        }
                        resultTableHtml += '<td rowspan="3"></td>';
                        resultTableHtml += '</tr>';
                        // 第二行
                        resultTableHtml += '<tr class="major-item-' + collegeName + '">';
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + Math.round(lnzymcMap[year].avgGrade - lnzymcMap[year].standardGrade).toFixed(1) + '</td>';
                        }
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + Math.ceil(lnzymcMap[year].avgRanking) + '</td>';
                        }
                        resultTableHtml += '</tr>';
                        // 第三行
                        resultTableHtml += '<tr class="major-item-' + collegeName + '">';
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + Math.round(lnzymcMap[year].lowGrade - lnzymcMap[year].standardGrade).toFixed(1) + '</td>';
                        }
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + lnzymcMap[year].lowRanking + '</td>';
                        }
                        resultTableHtml += '</tr>';
                    }
                    $(obj).parent().parent().next().next().after(resultTableHtml);
                    $(obj).text('收起专业详情');
                }
                $('#waitingDialog').modal("hide");
            }
        });
    } else {
        $(obj).parent().prop('rowspan', 3);
        $(obj).parent().parent().parent().find('tr.major-item-' + collegeName).remove();
        $(obj).text('查看专业详情');
    }
    showTableHead();
}

// 向下滚动页面时显示预测结果表格表头
function showTableHead() {
    $(window).unbind('scroll');
    var $resultTableContainer = $('div#resultTableContainer');
    var $resultTable = $resultTableContainer.children('table');
    var resultTableHeadHeight = $resultTable.children('caption').height() + $resultTable.children('thead').height();
    var $scrollHeader = $resultTable.clone();
    var $shelter = $("#shelter");
    $shelter.empty();
    $scrollHeader.appendTo('#shelter');
    $shelter.css({
        'height': resultTableHeadHeight + 18,
        'width': $resultTable.width(),
        'position': 'fixed',
        'top': '0',
        'overflow': 'hidden',
        'margin': '0 auto'
    });
    $shelter.find('table').css({'background-color': 'white'});
    $shelter.find('table caption').css({'background-color': 'white'});
    $shelter.find('table thead').css({'background-color': 'white'});
    $(window).scroll(function () {
        var scrollTop = (document.documentElement.scrollTop | document.body.scrollTop) - ($resultTable.offset().top + resultTableHeadHeight + 16);//判断是否到达窗口顶部
        if (scrollTop > 0) {
            $shelter.css("width", $resultTable.width() + 2);
            $shelter.show();
        } else {
            $shelter.hide();
        }
    });
}

/**
 * 显示当年招生计划
 *
 * @param obj
 * @param collegeName 院校名称
 */
function showDnzsjh(obj, collegeName) {
    if (collegeDnzsjhMap[collegeName]) {
        var tableHtml = '<table class="table table-bordered table-condensed table-hover">';
        tableHtml += '<thead>';
        tableHtml += '<tr>';
        tableHtml += '<th>专业名称</th>';
        tableHtml += '<th>招收人数</th>';
        tableHtml += '</tr>';
        tableHtml += '</thead>';
        tableHtml += '<tbody>';
        for (var major in collegeDnzsjhMap[collegeName]) {
            tableHtml += '<tr>';
            tableHtml += '<td>' + major + '</td>';
            tableHtml += '<td>' + collegeDnzsjhMap[collegeName][major] + '</td>';
            tableHtml += '</tr>';
        }
        tableHtml += '</tbody>';
        tableHtml += '</table>';
        var $dnzsjhPanel = $('#dnzsjhPanel');
        $dnzsjhPanel.draggable({
            cursor: 'move'
        });
        $dnzsjhPanel.find('.panel-title').html('<strong>' + collegeName + '</strong>今年招生计划');
        $dnzsjhPanel.find('.panel-body').find('.table-responsive').html(tableHtml);
        $dnzsjhPanel.css({'top': $(obj).offset().top + $(obj).height(), "left": $(obj).offset().left + $(obj).width()});

        $dnzsjhPanel.show();
    }
}

/**
 * 重置搜索条件
 */
function resetSearchConditions() {
    $searchConditionsContainer.find('input').val('');
    $searchConditionsContainer.find('select').val('').trigger('change');
}

function initAreaSelect() {
    var areaOptionsHtml = '';
    $.each(areas, function () {
        areaOptionsHtml += '<option value="' + this + '">' + this + '</option>';
    });
    $searchConditionsContainer.find('select[name=areaName]').select2({
        placeholder: '请选择省/市',
        language: 'zh-CN',
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
        language: 'zh-CN',
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
    if (!$searchConditionsContainer.find('input[name=studentName]').val()) {
        bootbox.alert("请输入姓名");
        return false;
    }

    if (!$searchConditionsContainer.find('input[name=examRegCode]').val()) {
        bootbox.alert("请输入准考证号");
        return false;
    }

    if (!$searchConditionsContainer.find('input[name=grade]').val()) {
        bootbox.alert("请输入高考分数");
        return false;
    }

    if (!$searchConditionsContainer.find('input[name=ranking]').val()) {
        bootbox.alert("请输入分数名次");
        return false;
    }

    if (!$searchConditionsContainer.find('select[name=batch]').val()) {
        bootbox.alert("请选择批次");
        return false;
    }

    return true;
}

function extractForm() {
    var requestForm = {};
    requestForm['studentName'] = $searchConditionsContainer.find('input[name=studentName]').val();
    requestForm['examRegCode'] = $searchConditionsContainer.find('input[name=examRegCode]').val();
    requestForm['grade'] = $searchConditionsContainer.find('input[name=grade]').val();
    requestForm['ranking'] = $searchConditionsContainer.find('input[name=ranking]').val();
    requestForm['category'] = $searchConditionsContainer.find('select[name=category]').val();
    requestForm['batch'] = $searchConditionsContainer.find('select[name=batch]').val();
    requestForm['areaName'] = $searchConditionsContainer.find('select[name=areaName]').val();
    requestForm['schoolType'] = $searchConditionsContainer.find('select[name=schoolType]').val();
    requestForm['targetSchool'] = $searchConditionsContainer.find('select[name=targetSchool]').val();
    requestForm['targetMajor'] = $searchConditionsContainer.find('input[name=targetMajor]').val();
    requestForm['algorithmType'] = $searchConditionsContainer.find('select[name=algorithmType]').val();
    requestForm['sortedType'] = $searchConditionsContainer.find('select[name=sortedType]').val();
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
