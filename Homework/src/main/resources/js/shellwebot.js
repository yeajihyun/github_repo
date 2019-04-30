// FireBase 세팅
firebase.initializeApp({
  apiKey: 'AIzaSyC96LjLbm8HUiIf0sqcFqfxykCb2CBlD5A',
  authDomain: 'restapitest-3f6d1.firebaseapp.com',
  projectId: 'restapitest-3f6d1'
});

// Initialize Cloud Firestore through Firebase
var db = firebase.firestore();
var pointColl = db.collection("point");


/*
모든 요리사에 대한 모든 평가 항목 포인트 조회
ex) getTotalPoint(1, 2) => 1회차 방송의 2번 평가자가 모든 요리사들을 평가한 모든 평가 항목 포인트 조회
return data(JSON)
code : 200(정상), 300(데이터없음), 400(에러)
size : data array size
data[] : broadSeq(방송회차1, apprSeq(평가자), cookSeq(요리사)
        ,point1(1번항목평가), point2(2번항목평가), point3(3번항목평가),  avgPoint(평가평균)
*/
/*
function getTotalPoint(broadSeq, apprSeq){
	var result = {};
	var index = 0;
	
	pointColl.where("broadSeq", "==", broadSeq)
	         .where("apprSeq", "==", apprSeq)
	         .get().then(function(querySnapshot) {
	        	 if( !querySnapshot.empty ) {
					 result.code = 200;
					 result.size = querySnapshot.size;
					 result.data = [];
					 querySnapshot.forEach(function(doc) {
						 // doc.data() is never undefined for query doc snapshots
						 result.data[index] = doc.data();
						 index = index + 1;
					 }); 
					 
					 console.log("result => ", result);
	        	 }
	        	 else{
					 result.code = 300;
					 console.log("result => ", result);
	        	 }
	})
	.catch(function(error) {
		result.code = 400;
        console.log("Error getting documents: ", error);
	    console.log("result => ", result);
    });
	
	return result;

}
*/


/*
모든 요리사에 대한 모든 평가 항목 포인트 조회
ex) getTotalPoint(1, 2) => 1회차 방송의 2번 평가자가 모든 요리사들을 평가한 모든 평가 항목 포인트 조회
return data(JSON)
code : 200(정상), 300(데이터없음), 400(에러)
data : broadSeq(방송회차1, apprSeq(평가자), cookSeq(요리사)
      ,point1(1번항목평가), point2(2번항목평가), point3(3번항목평가),  avgPoint(평가평균)
*/
function getPoint(broadSeq, apprSeq, cookSeq){
	// var result = {};
	var result = new Object();
	
	result.cookSeq = cookSeq;

	pointColl.where("broadSeq", "==", broadSeq)
	         .where("apprSeq", "==", apprSeq)
	         .where("cookSeq", "==", cookSeq)
	         .get().then(function(querySnapshot) {
	        	 if( !querySnapshot.empty ) {
					 result.code = 200;
					 querySnapshot.forEach(function(doc) {
						 // doc.data() is never undefined for query doc snapshots
						 result.data = doc.data();
						 console.log("result => ", result);
						 getPointCallBack(result);
					 }); 
	        	 }
	        	 else{
					 result.code = 300;
					 console.log("result => ", result);
					 getPointCallBack(result);
	        	 }
	}).catch(function(error) {
		result.code = 400;
        console.log("Error getting documents: ", error);
	    console.log("result => ", result);
	    getPointCallBack(result);
    });
}


/*
모든 요리사에 대한 모든 평가 항목 포인트 조회
ex) getAvgPoint(1, 2) => 1회차 방송의 2번 요리에 대한 모든 평가자 평균 포인트 조회
return data(JSON)
code : 200(정상), 300(데이터없음), 400(에러)
cookSeq : cookSeq(요리사)
data : avgPoint1(1번항목평가), avgPoint2(2번항목평가), avgPoint3(3번항목평가),  avgTotalPoint(평가평균)
*/
function getAvgPoint(broadSeq, cookSeq){
	// var result = {};
	var result = new Object();
	var sumAvgPoint1 = 0;
	var sumAvgPoint2 = 0;
	var sumAvgPoint3 = 0;
	var sumAvgTotalPoint = 0;
	var avgPoint1 = 0; 
	var avgPoint2 = 0; 
	var avgPoint3 = 0; 
	var avgTotalPoint = 0; 
	var index = 0;
	
	pointColl.where("broadSeq", "==", broadSeq)
	         .where("cookSeq", "==", cookSeq)
	         .get().then(function(querySnapshot) {
	        	 if( !querySnapshot.empty ) {
					 result.code = 200;
					 querySnapshot.forEach(function(doc) {
						 
						 // doc.data() is never undefined for query doc snapshots
						 sumAvgPoint1 += eval(doc.data().point1);
						 sumAvgPoint2 += eval(doc.data().point2);
						 sumAvgPoint3 += eval(doc.data().point3);
						 sumAvgTotalPoint += eval(doc.data().avgPoint);
						 
						 index++;
						 
						 if(index == querySnapshot.size) {

							avgPoint1 = sumAvgPoint1 / querySnapshot.size;
							avgPoint2 = sumAvgPoint2 / querySnapshot.size;
							avgPoint3 = sumAvgPoint3 / querySnapshot.size;
							avgTotalPoint = sumAvgTotalPoint / querySnapshot.size;

							result.data = {
									avgPoint1 : avgPoint1,
									avgPoint2 : avgPoint2,
									avgPoint3 : avgPoint3,
									avgTotalPoint : avgTotalPoint
							};
							
							result.cookSeq = cookSeq;
							console.log("result => ", result);
							getAvgPointCallBack(result);
						 }
					 }); 
	        	 }
	        	 else{
					 result.code = 300;
					 result.cookSeq = cookSeq;
					 console.log("result => ", result);
					 getAvgPointCallBack(result);
	        	 }
	        	 
	}).catch(function(error) {
		result.code = 400;
		result.cookSeq = cookSeq;
        console.log("Error getting documents: ", error);
	    console.log("result => ", result);
	    getAvgPointCallBack(result);
    });
}


/*
평가 포인트 입력
ex) setPoint(1, 2, 3, 4, 5, 6, 7) => 1회차 방송의 2번 평가자가 3번 요리사의 평가항목 순서대로 4점, 5점, 6점 및, 평균점수 7점 입력
return data(JSON)
code : 200(정상), 400(에러)
*/
function setPoint(broadSeq, apprSeq, cookSeq, point1, point2, point3, avgPoint){
	var result = {};
	
	// 이미 평가한 데이터가 있는지 확인
	pointColl.where("broadSeq", "==", broadSeq)
    .where("apprSeq", "==", apprSeq)
    .where("cookSeq", "==", cookSeq)
    .get().then(function(querySnapshot) {
    	// 이미 데이터 있으면 덮어쓰기
    	if( !querySnapshot.empty ) {
			querySnapshot.forEach(function(doc) {
				pointColl.doc(doc.id).set({
					broadSeq:broadSeq,
					apprSeq:apprSeq,
					cookSeq:cookSeq,
					point1:point1,
					point2:point2,
					point3:point3,
					avgPoint:avgPoint
				})
				.then(function() {
					result.code = 200;
				    console.log("Document Set successfully written!");
				    console.log("result => ", result);
					setPointCallBack(result);
				})
				.catch(function(error) {
					result.code = 400;
				    console.error("Error writing document: ", error);
				    console.log("result => ", result);
					setPointCallBack(result);
				});
			}); 
    	}
    	// 데이터 없으면 추가
    	else {
    		pointColl.add({
    			broadSeq:broadSeq,
    			apprSeq:apprSeq,
    			cookSeq:cookSeq,
				point1:point1,
				point2:point2,
				point3:point3,
				avgPoint:avgPoint
    		})
    		.then(function() {
				result.code = 200;
    		    console.log("Document Add successfully written!");
			    console.log("result => ", result);
				setPointCallBack(result);
    		})
    		.catch(function(error) {
				result.code = 400;
    		    console.error("Error writing document: ", error);
			    console.log("result => ", result);
				setPointCallBack(result);
    		});
    	}
	})
	.catch(function(error) {
		result.code = 400;
		console.log("Error getting documents: ", error);
	    console.log("result => ", result);
	});

}


/*
포인트 Reset 
ex) resetPoint(1, 2, 3, resetPointCallBack) => 1회차 방송의 2번 평가자의 3번 요리 점수 초기화
return data(JSON)
code : 200(정상), 300(데이터없음), 400(에러)
cookSeq : cookSeq(요리사)
*/
function resetPoint(broadSeq, apprSeq, cookSeq, resetPointCallBack){
	var result = {};
	
	result.cookSeq = cookSeq;

	pointColl.where("broadSeq", "==", broadSeq)
	         .where("apprSeq", "==", apprSeq)
	         .where("cookSeq", "==", cookSeq)
	         .get().then(function(querySnapshot) {
	        	 if( !querySnapshot.empty ) {
	        		 querySnapshot.forEach(function(doc) {
						 pointColl.doc(doc.id).delete().then(function() {
							 result.code = 200;
							 console.log("result => ", result);
							 resetPointCallBack(result);
						 }).catch(function(error) {
							result.code = 400;
					        console.log("Error delete documents", error);
						    console.log("result => ", result);
						    resetPointCallBack(result);
					    });
	        		 });
	        	 }
	        	 else{
					 result.code = 300;
					 console.log("result => ", result);
					 resetPointCallBack(result);
	        	 }
			}).catch(function(error) {
				result.code = 400;
				console.log("Error getting querySnapshot", error);
				console.log("result => ", result);
				resetPointCallBack(result);
			});
}