/**
 * @author Zhang Deng
 * Created on 2017/6/11.
 */

$.fn.select2.defaults.set("theme", "bootstrap");

var areas = [
    '北京', '广东', '山东', '江苏', '河南', '上海', '河北', '浙江', '陕西', '湖南', '重庆', '福建', '天津', '云南', '四川', '广西', '安徽', '海南', '江西', '湖北', '山西', '辽宁', '台湾', '黑龙江', '内蒙古', '香港', '澳门', '贵州', '甘肃', '青海', '新疆', '西藏', '吉林', '宁夏'
];

var $searchConditionsContainer = $('#searchConditionsContainer');

$(function () {
    acceptIntegerOnly($searchConditionsContainer.find('input[name=grade]'));
    acceptIntegerOnly($searchConditionsContainer.find('input[name=ranking]'));
    initAreaSelect();
    initSchoolSelect();

    $searchConditionsContainer.find('button[name=searchBtn]').click(function () {
        if (validateForm()) {
            $('#tipModel').modal("show");
            $.ajax({
                url: 'lnfsdxq/getTargetColleges',
                type: 'POST',
                cache: false,
                dataType: 'json',
                async: false,
                data: extractForm(),
                success: function (result) {
                    if (result && result.probabilityCalaDTOs && result.probabilityCalaDTOs.length > 0) {
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
                        resultTableHtml += '<th rowspan="2">高校类别</th>';
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
                            resultTableHtml += '<tr>';
                            resultTableHtml += '<td rowspan="3">' + (index + 1) + '</td>';
                            resultTableHtml += '<td rowspan="3">' + this.collegeCode + '</td>';
                            resultTableHtml += '<td rowspan="3">' + this.collegeName + '</td>';
                            resultTableHtml += '<td rowspan="3">' + (this.areaName == null ? '' : this.areaName) + '</td>';
                            resultTableHtml += '<td rowspan="3">' + this.batchName + '</td>';
                            resultTableHtml += '<td rowspan="3">' + (this.collegeType == null ? '' : this.collegeType) + '</td>';
                            resultTableHtml += '<td rowspan="3">' + (this.collegeRanking == null ? '' : this.collegeRanking) + '</td>';
                            // 第一行
                            for (var year in this.yxRankingMap) {
                                resultTableHtml += '<td rowspan="3">' + this.yxRankingMap[year].enrollCunt + '</td>';
                            }
                            for (var year in this.yxRankingMap) {
                                resultTableHtml += '<td>' + (this.yxRankingMap[year].standardGrade - this.yxRankingMap[year].highGrade) + '</td>';
                            }
                            resultTableHtml += '<td rowspan="3">' + (Math.round(this.xcfGaiLv * 10000) / 100).toFixed(2) + '%' + '</td>';
                            for (var year in this.yxRankingMap) {
                                resultTableHtml += '<td>' + this.yxRankingMap[year].highRanking + '</td>';
                            }
                            resultTableHtml += '<td rowspan="3">' + (Math.round(this.gaiLv * 10000) / 100).toFixed(2) + '%' + '</td>';
                            // for (var year in this.volunteerInfoMap) {
                            //     resultTableHtml += '<td rowspan="3">' + this.volunteerInfoMap[year] + '</td>';
                            // }
                            for (var i = 0; i < years.length; i++) {
                                resultTableHtml += '<td rowspan="3"></td>';
                            }
                            resultTableHtml += '<td rowspan="3"><a class="btn btn-primary btn-xs" onclick="showMajorDetails(\'' + this.collegeCode + '\', this)">查看专业详情</a></td>';
                            resultTableHtml += '</tr>';
                            // 第二行
                            resultTableHtml += '<tr>';
                            for (var i = 0; i < years.length; i++) {
                                resultTableHtml += '<th>' + (this.yxRankingMap[years[i]].standardGrade - this.yxRankingMap[years[i]].avgGrade) + '</th>';
                            }
                            for (i = 0; i < years.length; i++) {
                                resultTableHtml += '<th>' + this.yxRankingMap[years[i]].avgRanking + '</th>';
                            }
                            resultTableHtml += '</tr>';
                            // 第三行
                            resultTableHtml += '<tr>';
                            for (i = 0; i < years.length; i++) {
                                resultTableHtml += '<th>' + (this.yxRankingMap[years[i]].standardGrade - this.yxRankingMap[years[i]].LowGrade) + '</th>';
                            }
                            for (i = 0; i < years.length; i++) {
                                resultTableHtml += '<th>' + this.yxRankingMap[years[i]].LowRanking + '</th>';
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
                    $('#tipModel').modal("hide");
                }
            });
        }
    });

    $searchConditionsContainer.find('button[name=resetSearchConditionBtn]').click(function () {
        resetSearchConditions();
    });
});

function showMajorDetails(collegeCode, obj) {
    if ($(obj).text() == '查看专业详情') {
        var requestForm = extractForm();
        requestForm['collegeCode'] = collegeCode;
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
                        resultTableHtml += '<tr class="major-item-' + collegeCode + '">';
                        resultTableHtml += '<td rowspan="3" colspan="4"></td>';
                        resultTableHtml += '<td rowspan="3">' + probabilityCalaDTO.batchName + '</td>';
                        resultTableHtml += '<td rowspan="3">' + (probabilityCalaDTO.collegeType == null ? '' : probabilityCalaDTO.collegeType) + '</td>';
                        resultTableHtml += '<td rowspan="3">' + major + '</td>';

                        var lnzymcMap = majorEnrollDTOMap[major].lnzymcMap;
                        // 第一行
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td rowspan="3">' + lnzymcMap[year].enrollCunt + '</td>';
                        }
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + (lnzymcMap[year].standardGrade - lnzymcMap[year].highGrade) + '</td>';
                        }
                        resultTableHtml += '<td rowspan="3">' + (Math.round(majorEnrollDTOMap[major].xcfGaiLv * 10000) / 100).toFixed(2) + '%' + '</td>';
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + lnzymcMap[year].highRanking + '</td>';
                        }
                        resultTableHtml += '<td rowspan="3">' + (Math.round(majorEnrollDTOMap[major].gaiLv * 10000) / 100).toFixed(2) + '%' + '</td>';
                        // for (var year in this.volunteerInfoMap) {
                        //     resultTableHtml += '<td rowspan="3">' + this.volunteerInfoMap[year] + '</td>';
                        // }
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td rowspan="3"></td>';
                        }
                        resultTableHtml += '<td rowspan="3"></td>';
                        resultTableHtml += '</tr>';
                        // 第二行
                        resultTableHtml += '<tr class="major-item-' + collegeCode + '">';
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + (lnzymcMap[year].standardGrade - lnzymcMap[year].avgGrade) + '</td>';
                        }
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + lnzymcMap[year].avgRanking + '</td>';
                        }
                        resultTableHtml += '</tr>';
                        // 第三行
                        resultTableHtml += '<tr class="major-item-' + collegeCode + '">';
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + (lnzymcMap[year].standardGrade - lnzymcMap[year].LowGrade) + '</td>';
                        }
                        for (var year in lnzymcMap) {
                            resultTableHtml += '<td>' + lnzymcMap[year].LowRanking + '</td>';
                        }
                        resultTableHtml += '</tr>';
                    }
                    $(obj).parent().parent().next().next().after(resultTableHtml);
                    $(obj).text('收起专业详情');
                }
            }
        });
    } else {
        $(obj).parent().prop('rowspan', 3);
        $(obj).parent().parent().parent().find('tr.major-item-' + collegeCode).remove();
        $(obj).text('查看专业详情');
    }
    showTableHead();
}

// 向下滚动页面时显示预测结果表格表头
function showTableHead() {
    var $resultTableContainer = $('div#resultTableContainer');
    var $resultTable = $resultTableContainer.children('table');
    var resultTableHeadHeight = $resultTable.children('caption').height() + $resultTable.children('thead').height();
    var $scrollHeader = $resultTable.clone();
    var $shelter = $("#shelter");
    $shelter.empty();
    $scrollHeader.appendTo('#shelter');
    $shelter.css({
        'height': resultTableHeadHeight + 16,
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
            $shelter.show();
        } else {
            $shelter.hide();
        }
    });
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
    if (!$searchConditionsContainer.find('input[name=name]').val()) {
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
    requestForm['name'] = $searchConditionsContainer.find('input[name=name]').val();
    requestForm['examRegCode'] = $searchConditionsContainer.find('input[name=examRegCode]').val();
    requestForm['grade'] = $searchConditionsContainer.find('input[name=grade]').val();
    requestForm['ranking'] = $searchConditionsContainer.find('input[name=ranking]').val();
    requestForm['category'] = $searchConditionsContainer.find('select[name=category]').val();
    requestForm['batch'] = $searchConditionsContainer.find('select[name=batch]').val();
    requestForm['areaName'] = $searchConditionsContainer.find('select[name=areaName]').val();
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
