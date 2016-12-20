Object subclass: #KMeans
	| centroids |
	centroids := points first: clusters.
	iterations timesRepeat: [
		centroids := (self cluster: points around: centroids) collect: [ :each | each average ]
	 ].
	^ self cluster: points around: centroids! !
	^ (points groupedBy: [ :p | p closestFrom: centroids ]) values! !
	times timesRepeat: [ self run: points ].! !
	| time |
	time := Time millisecondsToRun: [ self run: points times: repetitions ].
	^ time / repetitions.! !
	iterations := numIterations.
