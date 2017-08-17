(function($){
    var ms = {
        init:function(totalsubpageTmep,args){
            return (function(){
                ms.fillHtml(totalsubpageTmep,args);
                ms.bindEvent(totalsubpageTmep,args);
            })();
        },
        //填充html
        fillHtml:function(totalsubpageTmep,args){
            var _this = totalsubpageTmep;
            return (function(){
                totalsubpageTmep="<li class=\"prev\"><a href=\"javascript:void(0);\" class='prev' style=\"margin-left: 15px;color: #333;\">&lt;</a></li>";
                // 页码大于等于4的时候，添加第一个页码元素
                if(args.currPage!=1 && args.currPage>=4 && args.totalPage!=4) {
                    totalsubpageTmep += "<li class='page'><a href='javascript:void(0);' style=\"margin-left: 15px;color: #333;\" class='geraltTb_pager' data-go='' >"+1+"</a></li>";
                }
                /* 当前页码>4, 并且<=总页码，总页码>5，添加“···”*/
                if(args.currPage-2>2 && args.currPage<=args.totalPage && args.totalPage>5) {
                    totalsubpageTmep += "<li class='page'><a href='javascript:void(0);' style=\"margin-left: 15px;color: #333;\" class='geraltTb_' data-go='' >...</a></li>";
                }
                /* 当前页码的前两页 */
                var start = args.currPage-2;
                /* 当前页码的后两页 */
                var end = args.currPage+2;

                if((start>1 && args.currPage<4) || args.currPage==1) {
                    end++;
                }
                if(args.currPage>args.totalPage-4 && args.currPage>=args.totalPage) {
                    start--;
                }
                for(; start<=end; start++) {
                    if(start<=args.totalPage && start>=1) {
                        if(start == args.currPage){
                            totalsubpageTmep += "<li class='page active'><a href='javascript:void(0);' style=\"margin-left: 15px;color: #333;\" class='geraltTb_pager' data-go='' >"+start+"</a></li>";
                        }else{
                            totalsubpageTmep += "<li class='page'><a href='javascript:void(0);' style=\"margin-left: 15px;color: #333;\" class='geraltTb_pager' data-go='' >"+start+"</a></li>";
                        }
                    }
                }
                if(args.currPage+2<args.totalPage-1 && args.currPage>=1 && args.totalPage>5) {
                    totalsubpageTmep += "<li class='page'><a href='javascript:void(0);' style=\"margin-left: 15px;color: #333;\" class='geraltTb_' data-go='' >...</a></li>";
                }

                if(args.currPage!=args.totalPage && args.currPage<args.totalPage-2 && args.totalPage!=4) {
                    totalsubpageTmep += "<li class='page'><a href='javascript:void(0);' style=\"margin-left: 15px;color: #333;\" class='geraltTb_pager' data-go='' >"+args.totalPage+"</a></li>";
                }

                totalsubpageTmep += "<li class=\"next\"><a href=\"javascript:void(0);\" class='next' style=\"margin-left: 15px;color: #333;\">&gt;</a></li>";

                $(".pagination").html(totalsubpageTmep);
            })();
        },

        handle: function(current, totalsubpageTmep, args){
            ms.fillHtml(totalsubpageTmep,{"currPage":current,"totalPage":args.totalPage,"turndown":args.turndown});
            if(typeof(args.backFn)=="function"){
                args.backFn(current);
            }
        },

        //绑定事件
        bindEvent:function(totalsubpageTmep,args){
            return (function(){
                if(args.currPage == args.totalPage){
                    totalsubpageTmep.find('.next').addClass("disabled");
                }
                if(args.currPage == 1){
                    totalsubpageTmep.find('.prev').addClass("disabled");
                }
                totalsubpageTmep.on("click","a.geraltTb_pager",function(event){
                    var current = parseInt($(this).text());
                    ms.handle(current, totalsubpageTmep, args);
                });
                // 上一页
                totalsubpageTmep.on("click","a.prev",function(event){
                    if($(this).hasClass('disabled')) return;
                    var current = parseInt(totalsubpageTmep.find('.active').find('a:first').text()) - 1;
                    ms.handle(current, totalsubpageTmep, args);
                });
                // 下一页
                totalsubpageTmep.on("click","a.next",function(event){
                    if($(this).hasClass('disabled')) return;
                    var current = parseInt(totalsubpageTmep.find('.active').find('a:first').text()) + 1;
                    ms.handle(current, totalsubpageTmep, args);
                });
            })();
        }
    }
    $.fn.createPage = function(options){
        ms.init(this,options);
    }
})(jQuery);