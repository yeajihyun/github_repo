firebase.initializeApp({
  apiKey: 'AIzaSyC96LjLbm8HUiIf0sqcFqfxykCb2CBlD5A',
  authDomain: 'restapitest-3f6d1.firebaseapp.com',
  projectId: 'restapitest-3f6d1'
});

// Initialize Cloud Firestore through Firebase
var db = firebase.firestore();
var pointColl = db.collection("point");


// 모든 요리사에 대한 모든 평가 항목 포인트 조회
// ex) getTotalPoint(1, 2) : 1회차 방송의 2번 평가자가 모든 요리사들을 평가한 모든 평가 항목 포인트 조회
function getTotalPoint(broadSeq, apprSeq){
	
	pointColl.where("broadSeq", "==", broadSeq)
	         .where("apprSeq", "==", apprSeq)
	         .get().then(function(querySnapshot) {
	        	 if( !querySnapshot.empty ) {
					 querySnapshot.forEach(function(doc) {
						 // doc.data() is never undefined for query doc snapshots
						 console.log(doc.id, " => ", doc.data());
						 return doc.data();
					 }); 
	        	 }
	        	 else{
	        		 console.log("No Data");
	        	 }
	})
	.catch(function(error) {
        console.log("Error getting documents: ", error);
    });

	/*
	pointColl.doc("1").get().then(function(doc) {

		if (doc.exists) {
			console.log("Point Data : ", doc.data());
			return doc.data();
		}
		else {
			console.log("No Data");
		}

	}).catch(function(error) {
		 console.log("Error getting document:", error);
	});
	*/

}


// 평가 포인트 입력
// ex) seetPoint(1, 2, 3, 1, 5) : 1회차 방송의 2번 평가자가 3번 요리사의 1번 평가항목에 대해 5포인트 입력
function setPoint(broadSeq, apprSeq, cookSeq, evalSeq, point){
	
	// 이미 평가한 데이터가 있는지 확인
	pointColl.where("broadSeq", "==", broadSeq)
    .where("apprSeq", "==", apprSeq)
    .where("cookSeq", "==", cookSeq)
    .where("evalSeq", "==", evalSeq)
    .get().then(function(querySnapshot) {
    	// 이미 데이터 있으면 덮어쓰기
    	if( !querySnapshot.empty ) {
			querySnapshot.forEach(function(doc) {
				pointColl.doc(doc.id).set({
					broadSeq:broadSeq,
					apprSeq:apprSeq,
					cookSeq:cookSeq,
					evalSeq:evalSeq,
					point:point
				})
				.then(function() {
				    console.log("Document Set successfully written!");
				})
				.catch(function(error) {
				    console.error("Error writing document: ", error);
				});
			}); 
    	}
    	// 데이터 없으면 추가
    	else {
    		pointColl.add({
    			broadSeq:broadSeq,
    			apprSeq:apprSeq,
    			cookSeq:cookSeq,
    			evalSeq:evalSeq,
    			point:point
    		})
    		.then(function() {
    		    console.log("Document Add successfully written!");
    		})
    		.catch(function(error) {
    		    console.error("Error writing document: ", error);
    		});
    	}
	})
	.catch(function(error) {
	console.log("Error getting documents: ", error);
	});

}