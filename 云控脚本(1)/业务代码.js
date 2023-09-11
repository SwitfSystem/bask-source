importClass(com.stardust.autojs.core.accessibility.AccessibilityBridge.WindowFilter);
let bridge = runtime.accessibilityBridge;
let bridgeField = runtime.getClass().getDeclaredField("accessibilityBridge");
let configField = bridgeField.getType().getDeclaredField("mConfig");
configField.setAccessible(true);
configField.set(bridge, configField.getType().newInstance());
bridge.setWindowFilter(new JavaAdapter(AccessibilityBridge$WindowFilter, {
    filter: function(info) {
        return true;
    }
}));

importClass(android.widget.Toast);
importClass(android.provider.Settings);
importClass(android.widget.Button);
importClass(android.view.View);
importClass('android.view.WindowManager');
importClass('android.view.Gravity');
importClass('android.graphics.PixelFormat');

app.startActivity({
    action:"android.intent.action.VIEW",
    data: android.net.Uri.parse("tmall://page.tm?https://market.m.taobao.com/app/tb-vip/88vip-benefit-detail/home.html?spm=a211oj.14003525.middletmallpoint.d_N&strategyCode=bed83830a6144e7d90af7741d91383a3&benefitCode=0b4a32763599467da35ed45b1f7bf74d&channel=lafite_point_reward&qq-pf-to=pcqq.c2c"),
    packageName: "com.tmall.wireless",
    className: "com.tmall.wireless.webview.TMCommonWebViewActivity"
})


var myApp = {
    baseUrl: "http://124.222.147.48/", // 后台访问地址
    wbsurl: "ws://124.222.147.48:7000",
    width: device.width,
    height: device.height,
    currentVersion: app.versionName,
    packageName: "com.pp.app",
    app_version: "1.0.0",
    app_name: "pp",
    dy_version: "19.3.0",
    ks_version: "9.8.40.21868",

};
var common = {};
//封装方法
/**
 * 定时器-标记定义
 */
common.TimerInfo=[],
/**
 * 定时器-标记赋值
 * @param {*} id 
 */
common.TimeSign=function (id){
    common.TimerInfo[id] = Date.parse(new Date())/1000;
},

/**
 * 定时器-主程序
 * @param {*} id 
 * @param {*} diff 
 */
common.Timer= function(id,diff){
    let t1 =Date.parse(new Date())/1000;
    
    if(t1-common.TimerInfo[id] >= diff){
        return true;
    }else{
        return false;
    }
},
//仿真随机带曲线滑动  
//qx, qy, zx, zy, time 代表起点x,起点y,终点x,终点y,过程耗时单位毫秒
common.swipeEx=function(qx, qy, zx, zy, time) {
    var xxy = [time];
    var point = [];
    var dx0 = {
        "x": qx,
        "y": qy
    };
    var dx1 = {
        "x": random(qx - 100, qx + 100),
        "y": random(qy, qy + 50)
    };
    var dx2 = {
        "x": random(zx - 100, zx + 100),
        "y": random(zy, zy + 50),
    };
    var dx3 = {
        "x": zx,
        "y": zy
    };
    for (var i = 0; i < 4; i++) {

        eval("point.push(dx" + i + ")");

    };
    for (let i = 0; i < 1; i += 0.08) {
        xxyy = [parseInt(this.bezier_curves(point, i).x), parseInt(this.bezier_curves(point, i).y)]
        xxy.push(xxyy);
    }
    gesture.apply(null, xxy);
},
common.bezier_curves=function(cp, t) {
    cx = 3.0 * (cp[1].x - cp[0].x);
    bx = 3.0 * (cp[2].x - cp[1].x) - cx;
    ax = cp[3].x - cp[0].x - cx - bx;
    cy = 3.0 * (cp[1].y - cp[0].y);
    by = 3.0 * (cp[2].y - cp[1].y) - cy;
    ay = cp[3].y - cp[0].y - cy - by;

    tSquared = t * t;
    tCubed = tSquared * t;
    result = {
        "x": 0,
        "y": 0
    };
    result.x = (ax * tCubed) + (bx * tSquared) + (cx * t) + cp[0].x;
    result.y = (ay * tCubed) + (by * tSquared) + (cy * t) + cp[0].y;
    return result;
},
//向左滑动
common.slideToLeft=function(){
    common.swipeEx(device.width * 0.9, device.height * 0.5, device.width * 0.1,device.height * 0.5, 300);
},
//短滑动
common.shortSlide=function() {
    common.swipeEx(device.width * 0.5, device.height * 0.5, device.width * 0.5, device.height * 0.5 - 500, 300);
},
//底部短滑动
common.bshortSlide=function() {
    common.swipeEx(device.width * 0.5, device.height * 0.8, device.width * 0.5, device.height * 0.8 - 500, 300);
},
//滑动通用方法
common.slide=function() {
    common.swipeEx(device.width * 0.8, device.height * 0.6, device.width * 0.9, 0, 300)
},
/***区域点击 */
common.rgClick=function(region){
    if(click(random(region[0], region[0] + region[2]), random(region[1], region[1] + region[3]))){
        return true;
    }else{
        return false;
    }
},
/**
 * 随机取指定长度的字符
 * @param {*} len 
 */
common.randomString=function(len) {
　　len = len || 32;
　　var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
　　var maxPos = $chars.length;
　　var pwd = '';
　　for (i = 0; i < len; i++) {
　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
　　}
　　return pwd;
},
common.randomNum=function(minNum,maxNum){
    minNum = minNum/1000;
    maxNum = maxNum/1000;
    switch(arguments.length){ 
    case 1:
        return parseInt(Math.random()*minNum+1)*1000; 
        break;
    case 2: 
        return parseInt(Math.random()*(maxNum-minNum+1)+minNum)*1000; 
        break; 
    default: 
        return 0; 
        break; 
    } 
},
/**
 * 点击第i个子节点num次
 * @param {*} node 
 * @param {*} i 
 * @param {*} num 
 */
common.clickChildNum=function(node,i,num){
    if(node){
        let child = [node.child(i).bounds().centerX(),node.child(i).bounds().centerY(),100,100];
        
        for (let index = 0; index <= parseInt(num); index++) {
            click(child[0],child[1]);
            sleep(random(2000,3200));
        }
    }
}



function 通讯录删除指定联系人(phone){
    var array = java.lang.reflect.Array.newInstance(java.lang.String, 1);
    array[0] = android.provider.ContactsContract.PhoneLookup._ID;
    var Uri = android.net.Uri;
    var uri =  Uri.withAppendedPath(android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
    var c =  context.getContentResolver().query(uri, array, null, null, null);
    let phoneId = "";
    while(c.moveToNext()){
        phoneId = c.getLong(c.getColumnIndex(android.provider.ContactsContract.PhoneLookup._ID));
        break;
    }
    array[0] = String(phoneId);
    var i = context.getContentResolver().delete(android.provider.ContactsContract.RawContacts.CONTENT_URI, android.provider.ContactsContract.RawContacts._ID+"=?", array);
    log("del :"+i);
}


var dd_down_url = 'https://ip3604672695.out.azhimalayanvh.com/fs08/2022/01/14/8/120_c6738f2df7f8e19683613f1c2c6f812a.apk?yingid=web_space&packageid=500733563&did=0c078892bbbd4aec0f930b3ba6ce799f&ali_redirect_domain=alissl.ucdl.pp.uc.cn&ali_redirect_ex_ftag=497ff8f5e38bd1f335818b57f4985550435392bb22e8bf77&ali_redirect_ex_tmining_ts=1642868824&ali_redirect_ex_tmining_expire=3600&ali_redirect_ex_hot=100';

let url = "#在抖音，记录美好生活#【DOU+小助手】正在直播，来和我一起支持Ta吧。复制下方链接，打开【抖音】，直接观看直播！ https://v.douyin.com/8mruf1q/";
let msg = "6";
// setText(0,"6666");
// dd直播私信(url,msg)
//url:直播间链接 type:1 为直播评论 type:2私信主播  type:3 私信直播间的用户 count:私信直播间在线用户个数
// dd直播私信(url,msg,3,7);

// var a = className("com.lynx.tasm.behavior.ui.view.UIComponent").depth(10).drawingOrder(1).indexInParent(0).scrollable(true).visibleToUser(false).findOne(1000);
// a.scrollForward();

// 自检();


function dd直播私信(url,msg,type,count){
    toastLog("正在开始直播私信");
    backDdToIndex();
    //直播评论
    if(type == 1){
        while(true){
            if(随机点击(desc,"发送")){
                toastLog("发送成功");
                break;
            }
            let a = textMatches(/说点什么.*/).findOne(1000);
            if(随机点击(false,a)){
                a.click();
                setText(0,msg);
                continue;
            }
            if(随机点击(text,"搜索")){
                continue;
            }
            if(随机点击(desc,"搜索")){
                sleep(1000,2000);
                setText(url);
                continue;
            }
    
        }
    //私信主播
    }else if(type ==2){
        while(true){
            sleep(1000,2000);
            let j = desc("发送").visibleToUser(true).findOne(1000);
            if(j){
                setText(0,msg);
                随机点击(false,j)
                break;
            }
            let k = textMatches(/.*私信/).visibleToUser(true).findOne(1000);
            if(随机点击(false,k)){
                setText(0,msg);
                continue;
            }
            if(随机点击(desc,"更多")){
                continue;
            }
            let x = className("android.widget.ImageView").clickable(true).depth(9).drawingOrder(2).indexInParent(2).visibleToUser(true).findOne(1000);
            if(x) {
                x.click();
                continue;
            }
            let z = className("android.widget.Button").depth(16).clickable(true).indexInParent(4).drawingOrder(4).findOne(2000);
            if(z){
                z.click();
                continue;
            }
            if(随机点击(text,"搜索")){
                continue;
            }
            if(随机点击(desc,"搜索")){
                sleep(1000,2000);
                setText(url);
                continue;
            }
        }
    }else if(type == 3){
        while(true){
            sleep(1000,2000);
            let o = className("android.widget.RelativeLayout").clickable(true).depth(19).drawingOrder(1).indexInParent(0).visibleToUser(true).findOne(1000);
            if(o){
                o.click();
                break;
            }
            if(随机点击(text,"搜索")){
                continue;
            }
            if(随机点击(desc,"搜索")){
                sleep(1000,2000);
                setText(url);
                continue;
            }
        }
        //进入直播间
        let i = 1;
        while(i<=count) {
            sleep(random(1000,2000));
            var user = className("com.lynx.tasm.behavior.ui.text.FlattenUIText").depth(10).drawingOrder(1).visibleToUser(true).text(i).findOne(1000);
            if(user){
                随机点击(false,user)
                sleep(random(500, 800));
                let p  = className("android.widget.ImageView").clickable(true).depth(9).drawingOrder(2).indexInParent(2).visibleToUser(true).findOne(2000);
                if(p){
                    while(true){
                        sleep(random(1000, 2000));
                        let lxkf = text("抖音店铺").findOne(500);
                        if(lxkf){
                            let bt = className("android.widget.ImageView").depth(10).drawingOrder(1).indexInParent(0).visibleToUser().findOne(1000);
                            if(bt)bt.click();
                            sleep(500);
                            setText(0,msg);
                            sleep(500);
                        }
                        let j = desc("发送").visibleToUser(true).findOne(1000);
                        if(j){
                            setText(0,msg);
                            随机点击(false,j)
                            break;
                        }
                        let k = textMatches(/.*私信/).visibleToUser(true).findOne(1000);
                        if(随机点击(false,k)){
                            setText(0,msg);
                            continue;
                        }
                        if(随机点击(desc,"更多")){
                            continue;
                        }
                        let x = className("android.widget.ImageView").clickable(true).depth(9).drawingOrder(2).indexInParent(2).visibleToUser(true).findOne(1000);
                        if(x) {
                            x.click();
                            continue;
                        }
                    }
                    while(true){
                        sleep(500);
                        var user = className("com.lynx.tasm.behavior.ui.text.FlattenUIText").depth(10).drawingOrder(1).visibleToUser(true).text(i).findOne(1000);
                        if(!user){
                            back();
                            continue;
                        }else{
                            break;
                        }
                    }

                }else{
                    toastLog("主播可能有隐私设置！");
                    break
                }
            }else{
                //执行上滑 
                var ulistRect = className("com.lynx.tasm.behavior.ui.list.UIList").visibleToUser(true).findOne(1000);
                var rect = ulistRect.bounds();
                if(rect.width() > 0){
                    common.swipeEx(rect.right/2,rect.bottom-rect.bottom*0.01,rect.right/3,rect.top-rect.bottom*0.01,1500);
                    log(rect.left+" "+rect.right + " "+rect.top + " "+rect.bottom);
                }
                i--;
            }
            i++;
        }

    }
    


    // let a = desc("搜索").findOne(2000);
    // if(a){
    //     a.click();
    //     sleep(random(2000,3000));
    //     setText(0,url);
    //     if(随机点击(desc,"搜索")){
    //         let a = textMatches(/说点什么.*/).findOne(3000);
    //         if(a){
    //             if(随机点击(false,a)){
    //                 setText(msg);
    //                 sleep(random(500,1500));
    //                 desc("发送").findOne(3000).click();
    //                 toastLog("发送成功！");
    //             }
    //         }else{
    //             toastLog("进入直播间失败！");
    //         }
    //     }else{
    //         toastLog("点击搜索错误！");
    //     }
    // }else{
    //     toastLog("没有找到搜索按钮！")
    // }

}

//检查app版本号
function getVerName(package_name) {
    let pkgs = context.getPackageManager().getInstalledPackages(0).toArray();
    for (let i in pkgs) {
        if (pkgs[i].packageName.toString() == package_name) {
            return pkgs[i].versionName;
        };
    };
};

// 自检();
function 自检(){
    toastLog("正在开始电量检测");
    var dd_bb = getVerName("com.ss.android.ugc.aweme");
    let battery = device.getBattery();
    let isCharging = device.isCharging();
    if(battery<30&&!isCharging){
        toastLog("电量少于百分之30,请连接充电器重试！");
        return false;
    }
    sleep(1000);
    toastLog("正在设置屏幕常亮!")
    device.keepScreenDim();
    sleep(1000);
    toastLog("正在检测DD版本!");
    if (dd_bb != myApp.dy_version) {
        toastLog("DD版本不正确,点击下载");
        app.openUrl(dd_down_url);
        return false;
    }
    toastLog("正在进行账号检测，请勿操作手机！")
    backDdToIndex();
    sleep(500,1200);
    let f = false;
    while(true) {
        sleep(1000);
        let b = text("开始检测").findOne(1000);
        if(b){
            b.click();
            sleep(10000);
            break;
        }
        if(随机点击(text,"帐号检测")){
            continue;
        }
        if(随机点击(text,"创作者服务中心")){
            continue;
        }
        let a = className("android.widget.RelativeLayout").visibleToUser(true).depth(13).drawingOrder(10).indexInParent(4).findOne(1000);
        if(a&&f){
            a.click();
            continue;
        }
        let c = textMatches(/.*登录.*/).findOne(1000);
        if(c){
            toastLog("账号未登录，请先登录！");
            return false;
        }
        if(随机点击(text,"我")){
            f = true;
            continue;
        }
    }
    let stime = 3000;
    let b = text("开始检测").findOne(stime);
    if(!b){
        let c = textMatches(/.*状态正常.*/).findOne(2000);
        if(c){
            toastLog("自检通过！");
            return true;
        }else{
            toastLog("账号异常！如有问题，请联系客服！");
            return false;
        }
    }else{
        toastLog("发生异常,重新检测!");
        stime+=1000;
        自检();
    }
}




//回到Dd首页
function backDdToIndex() {
    launchApp("抖音");
    let 抖音 = text("抖音").findOnce()
    if (抖音) {
        抖音.click()
    }
    let 抖音2 = desc("抖音").findOnce()
    if (抖音2) {
        抖音2.click()
    }
    waitForPackage("com.ss.android.ugc.aweme");
    sleep(random(2000, 3000));
    for (let j = 0; j < 10; j++) {
        if (!text("首页").visibleToUser().exists()) {  
            back();
            sleep(random(500,1000));
        } else {
            随机点击(text,"首页");
            j = 10;
        }
    }
}

//http访问
function http访问(链接) {
    http.__okhttp__.setTimeout(10000);
    while (true) {
        try {
            // 捕捉所有异常
            res = http.get(链接, {
                headers: {
                    'Accept-Language': 'zh-cn,zh;q=0.5',
                    'User-Agent': 'Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36'
                }
            })
            if (res.statusCode == 200) {
                var 返回值 = String(res.url)
                return 返回值
            } else {
                log("请求失败:" + res.statusMessage);
            }
        } catch (e) {
            // 出现异常返回null

        }
        sleep(1000)
    }
}
function 随机点击(属性, 值) {
    let a

    if (属性 && 值) {
        a = 属性(值).visibleToUser().findOnce()
    }

    if (!属性 && 值) {
        a = 值
    }

    if (a) {
        x1 = a.bounds().left + 10
        y1 = a.bounds().top + 10
        x2 = a.bounds().right - 10
        y2 = a.bounds().bottom - 10
        xx = random(x2, x1)
        yy = random(y2, y1)
        click(xx, yy)
        sleep(3000)
        return true
    }
    return false
}