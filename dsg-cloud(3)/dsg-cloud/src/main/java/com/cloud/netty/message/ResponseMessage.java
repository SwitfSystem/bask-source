package com.cloud.netty.message;

/**
 * 向websocket客户端发送执行命令响应体
 * @author touch
响应示例:
{
	"code":0,
	"msg":"任务类型",
	"platform":0,
	"content":"任务具体信息",
	"img":"http://图片地址",
	"video":"http://视频地址",
	"audio":"http://音频地址"
}

 */
public class ResponseMessage {
	/**
	 * 执行命令代号
	 * 0 无需执行任何命令
	 * 1 重启
	 * 2 关机
	 * 3 锁屏
	 * 4 亮屏
	 * 5 点赞视频
	 * 6 关注UP主
	 * 7 收藏视频
	 * 8 评论视频
	 * 11 账号私信
	 * 12 直播私信
	 * 13 评论私信
	 * 21 超级热度
	 * 22 直播暖场
	 * 23 实时互动
	 * 24 疯狂点屏
	 * 25 关注榜1-榜10 
	 */
	private Integer code;
	
	/**
	 * 命令含义
	 */
	private String msg;
	
	/**
	 * 平台代号
	 * 0 抖音
	 * 1 快手
	 */
	private Integer platform;
	
	/**
	 * 图片/表情url地址
	 */
	private String img;
	
	/**
	 * 文本信息
	 */
	private String content;
	
	/**
	 * 视频url地址
	 */
	private String video;
	
	/**
	 * 语音url地址
	 */
	private String audio;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}
	
}
