package com.mahor.szlaki

class TrailListMediumFragment : TrailListFragment() {
    override fun observeTrails(adapter: TrailListAdapter) {
        trailViewModel.mediumTrails.observe(viewLifecycleOwner) { trails ->
            trails.let { adapter.submitList(it)}
        }
    }
}