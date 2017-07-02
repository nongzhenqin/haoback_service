/**
 * Created by nong on 2017/6/26.
 */
var ScrollTop;

ScrollTop = (function () {
    function ScrollTop() {
        this.containerTag = $('#nav');
        this.bottomHeigh = 200;
        this.leftWidth = 50;
        this.addStyle();
        this.show();
        $(window).resize((function (_this) {
            return function () {
                return _this.show();
            };
        })(this));
        $(document).on('click', '#scrollTop', (function (_this) {
            return function () {
                return $('body,html').animate({
                    scrollTop: 0
                }, 500);
            };
        })(this));
    }

    ScrollTop.prototype.addStyle = function () {
        var css;
        css = "<style>#scrollTop{ position: fixed; z-index: 10000; background: url(\"https://img.alicdn.com/imgextra/i3/70061495/TB28vummXXXXXaEXXXXXXXXXXXX_!!70061495.png\") no-repeat; width: 54px; height: 54px; cursor: pointer; }</style>";
        return $('head').append(css);
    };

    ScrollTop.prototype.show = function () {
        var html, position;
        $('#scrollTop').remove();
        position = this.calcPosition();
        html = "<div id='scrollTop' style='left:" + position.left + "px;top:" + position.top + "px;'></div>";
        return $('body').append(html);
    };

    ScrollTop.prototype.calcPosition = function () {
        var containerPosition, containerWidth, left, top, windowHeight;
        containerPosition = $(this.containerTag).offset();
        containerWidth = $(this.containerTag).width();
        windowHeight = $(window).height();
        left = containerPosition.left + containerWidth + this.leftWidth;
        top = windowHeight - this.bottomHeigh;
        return {
            left: left,
            top: top
        };
    };

    return ScrollTop;

})();
