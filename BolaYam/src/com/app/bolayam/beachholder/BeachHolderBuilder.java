package com.app.bolayam.beachholder;

import java.util.ArrayList;
import java.util.List;

import com.application.imageholders.ImageHolder;
import com.application.models.BeachModel;
import com.application.models.GenericModel;
import com.application.utils.StringUtil;

public class BeachHolderBuilder {

	public static ArrayList<ImageHolder> createBeachHolders(List<BeachModel> list,String imageKey){
		ArrayList<ImageHolder> holders = new ArrayList<ImageHolder>();
		
		for(GenericModel model : list){
			BeachModel beach = (BeachModel) model;
			
			ImageHolder beachHolder = new BeachImageHolder(model.getid(),model.getName(),beach.getWhether(),beach.getAttractionsDescription(), model.getDescription(),getImage(model)
					,beach.getMensNumber(),beach.getWomenNumber(),beach.getScore(),beach.getLikes(),beach.getComments(),0);
			holders.add(beachHolder);			
		}
		
		return holders;
	}

	private static String getImage(GenericModel model) {
		if(StringUtil.isEmpty( model.getImage())){
			return "data:image/jpeg;base64,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";
		}
		return  model.getImage();
	}
}