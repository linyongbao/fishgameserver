package com.fish.server.web.controller.captcha;

import java.awt.Color;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fish.server.web.common.CommonConstants;

//kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no
//kaptcha.border.color   边框颜色   默认为Color.BLACK
//kaptcha.border.thickness  边框粗细度  默认为1
//kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha
//kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator
//kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx
//kaptcha.textproducer.char.length   验证码文本字符长度  默认为5
//kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
//kaptcha.textproducer.font.size   验证码文本字符大小  默认为40
//kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK
//kaptcha.textproducer.char.space  验证码文本字符间距  默认为2
//kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise
//kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK
//kaptcha.obscurificator.impl   验证码样式引擎  默认为WaterRipple
//kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer
//kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground
//kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY
//kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE
//kaptcha.image.width   验证码图片宽度  默认为200
//kaptcha.image.height  验证码图片高度  默认为50 
/**
 * 邮件
 * 
 * @author www.inxedu.com
 *
 */
@Controller
@RequestMapping("/ran")
public class CaptchaController {

	private static Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
	private static Random random = new Random();
	static {
		// cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
		cs.setColorFactory(new ColorFactory() {
			@Override
			public Color getColor(int x) {
				int[] c = new int[3];
				int i = random.nextInt(c.length);
				for (int fi = 0; fi < c.length; fi++) {
					if (fi == i) {
						c[fi] = random.nextInt(71);
					} else {
						c[fi] = random.nextInt(256);
					}
				}
				return new Color(c[0], c[1], c[2]);
			}
		});
		RandomWordFactory wf = new RandomWordFactory();
		wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
		wf.setMaxLength(4);
		wf.setMinLength(4);
		cs.setWordFactory(wf);
	}

	@RequestMapping("/random")
	@ResponseBody
	public Map random(HttpServletRequest request, HttpServletResponse response) throws Exception {

		switch (random.nextInt(5)) {
		case 0:
			cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
			break;
		case 1:
			cs.setFilterFactory(new MarbleRippleFilterFactory());
			break;
		case 2:
			cs.setFilterFactory(new DoubleRippleFilterFactory());
			break;
		case 3:
			cs.setFilterFactory(new WobbleRippleFilterFactory());
			break;
		case 4:
			cs.setFilterFactory(new DiffuseRippleFilterFactory());
			break;
		}
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		setResponseHeaders(response);
		String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
		session.setAttribute(CommonConstants.RAND_CODE, token);

		return null;
	}

	protected void setResponseHeaders(HttpServletResponse response) {
		response.setContentType("image/png");
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		long time = System.currentTimeMillis();
		response.setDateHeader("Last-Modified", time);
		response.setDateHeader("Date", time);
		response.setDateHeader("Expires", time);
	}

}
